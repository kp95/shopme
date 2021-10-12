package com.shopme.common;

public class Constants {

	public static final String S3_BASE_URI;
	
	static {
		String bucketName = System.getenv("AWS_BUCKET_NAME");
		String region = System.getenv("AWS_REGION");
		
		String pattern = "http://%s.s3.%s.amazonaws.com";
		
		String uri = String.format(pattern, bucketName,region);
		S3_BASE_URI = bucketName == null ? "" :uri;
		
	}
	
}
