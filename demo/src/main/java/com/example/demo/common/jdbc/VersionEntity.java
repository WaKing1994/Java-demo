package com.example.demo.common.jdbc;

import com.example.demo.common.jdbc.BaseEntity;

@javax.persistence.MappedSuperclass
public class VersionEntity extends BaseEntity {

	private static final long serialVersionUID = -4084133914872862754L;

	@Comment(value={"版本号"})
	private Long version = 0L;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public static class JdbcVisitException extends RuntimeException {
		private static final long serialVersionUID = 1366070108325380190L;
		
		public JdbcVisitException() {
			this("当前操作人数过多请稍后再试");
		}
		
		public JdbcVisitException(String msg) {
			super(msg);
		}
	}
}
