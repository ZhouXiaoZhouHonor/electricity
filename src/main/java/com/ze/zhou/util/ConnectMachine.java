package com.ze.zhou.util;

import java.util.Enumeration;

import gnu.io.CommPortIdentifier;

/*
	author:zhouze
	@createTime:2020年5月19日
	@goal:
*/
public class ConnectMachine {
	public static void main(String[]args) {
		System.out.println("zxc");
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> em = CommPortIdentifier.getPortIdentifiers();
		while (em.hasMoreElements()) {
			String name = em.nextElement().getName();
			System.out.println(name+"asd");
		}
		System.out.println("q3we");
	}
}
