package com.wafersystems.mrbs.vo.meeting;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wafersystems.mrbs.vo.user.UserInfo;

@Entity
public class Media implements Serializable { 

	private static final long	serialVersionUID	= 975828566082068417L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int					id;   //编号
	
	@Column(name = "MEDIA_TYPE")
    private  int  mediaType;         //资源类型      1：网页，2：视频；3：文本 ；4：图片
	
	@Column(name = "MEDIA_NAME")
	private String	 medianame;    //资源名称
	
	@Column(name = "MEDIA_SIZE")
	private int					size;  //文件大小

	@Column(name = "MEDIA_LENGTH")
	private int	mediaLength;    //播放时长
 
	@Column(name = "AUDIT_USER")
	private String	 auditUser;  //创建人
	
	@Column(name = "AUDIT_TIME")
	private Date	 auditTime;  //创建人
	
	@Column(name = "FILEPATH")
	private String	 filepath;   //文件路径
	
	@Column(name = "FILENAME")
	private String	 filename;   //文件路径
	
	@Column(name = "PATH_TYPE")
	private int	 pathType;      // 资源路径来源    1：本地上传  2：网络连接
	
	@Column(name = "MEDIA_URL")
	private String				mediaUrl;   //资源地址
	
	@Column(name = "description")
	private String description;  //描述信息
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 
	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public String getMedianame() {
		return medianame;
	}

	public void setMedianame(String medianame) {
		this.medianame = medianame;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMediaLength() {
		return mediaLength;
	}

	public void setMediaLength(int mediaLength) {
		this.mediaLength = mediaLength;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getPathType() {
		return pathType;
	}

	public void setPathType(int pathType) {
		this.pathType = pathType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	 

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
