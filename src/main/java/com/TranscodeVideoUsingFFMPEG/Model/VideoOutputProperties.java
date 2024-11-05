package com.TranscodeVideoUsingFFMPEG.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoOutputProperties {
	 public int height;
     public int video_bitrate;
     public String video_codec;
     public int audio_bitrate;
     public String audio_codec;
	@Override
	public String toString() {
		return "VideoOutputProperties [height=" + height + ", video_bitrate=" + video_bitrate + ", "
				+ (video_codec != null ? "video_codec=" + video_codec + ", " : "") + "audio_bitrate=" + audio_bitrate
				+ ", " + (audio_codec != null ? "audio_codec=" + audio_codec : "") + "]";
	}
     
     

}
