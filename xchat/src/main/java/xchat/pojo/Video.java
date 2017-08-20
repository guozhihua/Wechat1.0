package xchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xchat.sys.SuperVO;

/**
 * 视频
 * <br>
 * <b>功能：</b>VideoEntity<br>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video extends SuperVO {
	
	
	private String videoName;//
	private Long videoId;//
	private Integer videoUuid;//
	private String videoType;//
	private String videoHtml;//
}

