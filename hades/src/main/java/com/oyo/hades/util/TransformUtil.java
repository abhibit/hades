package com.oyo.hades.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TransformUtil {

	private static final Logger log = LoggerFactory.getLogger(com.oyo.hades.util.TransformUtil.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * Returns a JSON {@link String} from the <strong>obj</strong> provided using {@link ObjectMapper}
	 * @param obj - {@link Object}
	 * @return {@link String} - JSON string
	 */
	public static String toJson(Object obj) {
		try {
			if (obj != null) {
				return objectMapper.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			log.error("Error in toJson(), obj: " + obj + " ; Exception: " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Returns the parsed {@link Object} from the {@link String jsonString} provided using
	 * {@link ObjectMapper} - will need a type cast
	 * @param jsonString - {@link String}
	 * @param valueType - {@link Class}
	 * @return {@link Object}
	 */
	public static <T> T fromJson(String jsonString, Class<T> valueType) {
		try {
			if (jsonString != null) {
				return objectMapper.readValue(jsonString, valueType);
			}
		} catch (Exception e) {
			log.error(
					"Error in fromJson(), jsonString: " + jsonString + " ; Exception: " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Returns the parsed {@link Object} from the {@link String jsonString}
	 * provided using {@link ObjectMapper} - will need a type cast
	 * @param <T>
	 * @param jsonString - {@link String}
	 * @param valueType - {@link TypeReference}
	 * @return {@link Object}
	 */
	public static <T> T fromJson(String jsonString, TypeReference<T> valueType) {
		try {
			if (jsonString != null) {
				return objectMapper.readValue(jsonString, valueType);
			}
		} catch (Exception e) {
			log.error("Error in fromJson(), jsonString: " + jsonString + " ; Exception: " + e.getMessage());
		}
		return null;
	}


}
