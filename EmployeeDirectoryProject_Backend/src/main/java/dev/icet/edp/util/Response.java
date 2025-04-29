package dev.icet.edp.util;

import dev.icet.edp.util.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
	private final T data;
	private final ResponseType status;
}
