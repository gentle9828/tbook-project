package com.tbooke.tbookeuser.dto.google;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleProfileResponseDto {
	private  String id;
	private  String email;
	private  String verified_email;
	private  String name;
	private  String given_name;
	private  String family_name;
	private  String picture;
	private  String locale;
}
