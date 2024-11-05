package com.TranscodeVideoUsingFFMPEG.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.TranscodeVideoUsingFFMPEG.Model.TranscodingModel;
import com.TranscodeVideoUsingFFMPEG.Model.VideoDestination;
import com.TranscodeVideoUsingFFMPEG.Model.VideoOutputProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TranscodingService {
	private static final Logger logger = LoggerFactory.getLogger(TranscodingService.class);

	public String videoResolution(String url) {
		logger.info("starting the transcoding using the url {}",url);
		File input = new File(url);
		String pixelFormat = "yuv420p";
		File[] listFiles = input.listFiles();
		if (listFiles.length == 0) {
			logger.info("there is no files in ths source link ");
			return "no Files in source link";
		}
		TranscodingModel transcodingModel = read();
		if (transcodingModel == null) {
			logger.error("The transcodingModel Instace was null");
			return "The transcodingModel instance was null";

		}

		List<VideoOutputProperties> ou = transcodingModel.outputs;

		VideoDestination vd = transcodingModel.destination;
		StringBuilder ffmpegCmdBuilder = new StringBuilder();
		File output = new File(vd.path);
		if (!output.exists()) {
			output.mkdirs();
		}
		for (File f : listFiles) {
			System.out.println(f.getName().replace(".mp4", ""));
			ffmpegCmdBuilder.append("ffmpeg -i ").append(f.getAbsolutePath()).append(" ").append(ou.stream()
					.map(o -> String.format(
							"-c:a %s -b:a %dk -c:v %s -vf scale=%d:%d -x264opts bitrate=%d -pix_fmt %s %s ",
							o.audio_codec, o.audio_bitrate, o.video_codec, -2, o.height, o.video_bitrate, pixelFormat,
							output + File.separator + f.getName().replace(".mp4", "") + o.height + ".mp4"))
					.reduce("", String::concat));
			String[] commands = ffmpegCmdBuilder.toString().split(" ");
			ProcessBuilder processBuilder = new ProcessBuilder(commands);

			try {
				// Start the process
				Process process = processBuilder.start();

				// Capture error stream output for debugging
				try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
					String line;
					while ((line = errorReader.readLine()) != null) {
						System.err.println(line);
					}
				}

				int exitCode = process.waitFor();
				if (exitCode == 0) {
					logger.info("transcoding completed");

				} else {
					logger.error("Error during transcoding process {}", exitCode);
					return "not completed +" + exitCode;
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}
		return "Transcoding completed";

	}

	public TranscodingModel read() {
		logger.error("Reading the properties from Json file");
		ObjectMapper objectMapper = new ObjectMapper();
		String de = "/input.json";
		InputStream in = getClass().getResourceAsStream(de);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			logger.info("Successfully mapped our json file to TrancosingModel object");
			return objectMapper.readValue(reader, TranscodingModel.class);
		} catch (IOException e) {
			logger.error("Error reading JSON file", e);
			e.printStackTrace();
			return null; // Handle the error as appropriate for your application
		}
	}

}
