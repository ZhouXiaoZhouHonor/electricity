package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月15日
	@goal:
*/
public class PileElectricity {
	private Integer pileElectricityId;
	private Date pileElectricityTime;
	private Float pileElectricityV;
	private Float pileElectricityI;
	private Float pileElectricityHz;
	private Float pileElectricityActivePower;
	private Float pileElectricityActiveEnergy;
	private Float pileElectricityReactiveEnergy;
	private Float electricityV;
	private Float electricityI;
	private Float electricityHz;
	private Float electricityActivePower;
	private Float electricityActiveEnergy;
	private Float electricityReactiveEnergy;
	private Pile pile;
	
	public Integer getPileElectricityId() {
		return pileElectricityId;
	}
	public void setPileElectricityId(Integer pileElectricityId) {
		this.pileElectricityId = pileElectricityId;
	}
	public Date getPileElectricityTime() {
		return pileElectricityTime;
	}
	public void setPileElectricityTime(Date pileElectricityTime) {
		this.pileElectricityTime = pileElectricityTime;
	}
	public Float getPileElectricityV() {
		return pileElectricityV;
	}
	public void setPileElectricityV(Float pileElectricityV) {
		this.pileElectricityV = pileElectricityV;
	}
	public Float getPileElectricityI() {
		return pileElectricityI;
	}
	public void setPileElectricityI(Float pileElectricityI) {
		this.pileElectricityI = pileElectricityI;
	}
	public Float getPileElectricityHz() {
		return pileElectricityHz;
	}
	public void setPileElectricityHz(Float pileElectricityHz) {
		this.pileElectricityHz = pileElectricityHz;
	}
	public Float getPileElectricityActivePower() {
		return pileElectricityActivePower;
	}
	public void setPileElectricityActivePower(Float pileElectricityActivePower) {
		this.pileElectricityActivePower = pileElectricityActivePower;
	}
	public Float getPileElectricityActiveEnergy() {
		return pileElectricityActiveEnergy;
	}
	public void setPileElectricityActiveEnergy(Float pileElectricityActiveEnergy) {
		this.pileElectricityActiveEnergy = pileElectricityActiveEnergy;
	}
	public Float getPileElectricityReactiveEnergy() {
		return pileElectricityReactiveEnergy;
	}
	public void setPileElectricityReactiveEnergy(Float pileElectricityReactiveEnergy) {
		this.pileElectricityReactiveEnergy = pileElectricityReactiveEnergy;
	}
	public Float getElectricityV() {
		return electricityV;
	}
	public void setElectricityV(Float electricityV) {
		this.electricityV = electricityV;
	}
	public Float getElectricityI() {
		return electricityI;
	}
	public void setElectricityI(Float electricityI) {
		this.electricityI = electricityI;
	}
	public Float getElectricityHz() {
		return electricityHz;
	}
	public void setElectricityHz(Float electricityHz) {
		this.electricityHz = electricityHz;
	}
	public Float getElectricityActivePower() {
		return electricityActivePower;
	}
	public void setElectricityActivePower(Float electricityActivePower) {
		this.electricityActivePower = electricityActivePower;
	}
	public Float getElectricityActiveEnergy() {
		return electricityActiveEnergy;
	}
	public void setElectricityActiveEnergy(Float electricityActiveEnergy) {
		this.electricityActiveEnergy = electricityActiveEnergy;
	}
	public Float getElectricityReactiveEnergy() {
		return electricityReactiveEnergy;
	}
	public void setElectricityReactiveEnergy(Float electricityReactiveEnergy) {
		this.electricityReactiveEnergy = electricityReactiveEnergy;
	}
	public Pile getPile() {
		return pile;
	}
	public void setPile(Pile pile) {
		this.pile = pile;
	}
	
	
}
