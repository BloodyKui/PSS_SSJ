package cn.lkk.pss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 密码输入错误次数过多，记录IP 一定时间内禁止该IP登录系统，并发邮件提示该用户,登录成功后还要抹除信息
 * 
 * @author Quid_Lee 2017年7月5日
 */
@Entity
@Table(name = "forbidip")
public class ForbidIP extends BaseDomain{
	@Column(name="pathip")
	private String path_IP;

	// 有可能有多个IP 去尝试登录同一个用户
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="emp_id")
	private Employee dangerEmp;
	
	private Integer wrong;

	public String getPath_IP() {
		return path_IP;
	}

	public void setPath_IP(String path_IP) {
		this.path_IP = path_IP;
	}

	public Employee getDangerEmp() {
		return dangerEmp;
	}

	public void setDangerEmp(Employee dangerEmp) {
		this.dangerEmp = dangerEmp;
	}

	public Integer getWrong() {
		return wrong;
	}

	public void setWrong(Integer wrong) {
		this.wrong = wrong;
	}

}
