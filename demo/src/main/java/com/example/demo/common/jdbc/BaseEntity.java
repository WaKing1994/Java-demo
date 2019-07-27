package com.example.demo.common.jdbc;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	public static String PROP_ID = "id";

	private static final long serialVersionUID = -3152035066540745359L;

	@Comment(value={"创建时间"})
	@XmlTransient
	private Date gmtCreated = new Date();

	@Comment(value={"最后修改时间"})
	@XmlTransient
	private Date gmtModify = new Date();

	@Comment(value={"编号"})
	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false, precision = 20, scale = 0)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

}
