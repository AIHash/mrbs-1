package com.wafersystems.mrbs.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Language implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 938253422117138295L;
	
	private String pin;
	private String name;
	private String fileName;
	
	/**
	 * 此为hibernate增强后的JPA的主键生成策略
	 * strategy可以为native、uuid、hilo、assigned、identity、select、sequence、seqhilo、increment、foreign、guid、uuid.hex、sequence-identity
	 * 详细信息见 <a>http://xiaogui9317170.javaeye.com/blog/283526</a>
	 * 
	 * name属性指定生成器名称。 
	 * strategy属性指定具体生成器的类名。 
	 * parameters得到strategy指定的具体生成器所用到的参数。
	 */
	@Id
	@GeneratedValue(generator="pinGenerator")
	@GenericGenerator(name="pinGenerator", strategy = "assigned", parameters={@Parameter(name = "property", value = "pin")})
	@Column(length=10)
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	@Column(nullable=false, length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=50)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
