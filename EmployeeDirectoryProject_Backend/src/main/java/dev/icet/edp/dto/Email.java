package dev.icet.edp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	private String to;
	private String subject;
	private String message;
}
