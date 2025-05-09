package com.shopnext.config;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

@Configuration
public class R2Req {
	@Value("${r2.api.uri}")
	String uri;
	@Value("${r2.api.sid}")
	String sid;
	@Value("${r2.api.skey}")
	String skey;
	@Value("${r2.api.host}")
	String host;
	String stringToSign;

	String signature;

	public static byte[] hmacSHA256(byte[] key, String data) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(key, "HmacSHA256"));
		return mac.doFinal(data.getBytes("UTF8"));
	}

	public ResponseEntity<byte[]> getRes(String imgname) throws Exception {
		String verb = "GET";

		String signedHeaders = "host;x-amz-content-sha256;x-amz-date";
		String payloadHash = "UNSIGNED-PAYLOAD";
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = today.format(formatter);
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
		String formatted = now.format(formatter1) + "Z";
		String canonicalHeaders = "host:" + this.host + "\n" +
				"x-amz-content-sha256:" + payloadHash + "\n" +
				"x-amz-date:" + formatted;
		String canonicalRequest = verb + "\n" + "/shop-next/product_images/"
				+ URLEncoder.encode(imgname, "UTF-8") + "\n\n"
				+ canonicalHeaders
				+ "\n\n" + signedHeaders + "\n"
				+ payloadHash;
		String sha256 = DigestUtils.sha256Hex(canonicalRequest);
		byte[] datekey = hmacSHA256(("AWS4" + skey).getBytes("UTF-8"), formattedDate);
		byte[] dateRegionKey = hmacSHA256(datekey, "us-east-1");
		byte[] dateRegionServiceKey = hmacSHA256(dateRegionKey, "s3");

		byte[] signingKey = hmacSHA256(dateRegionServiceKey, "aws4_request");
		this.stringToSign = "AWS4-HMAC-SHA256" + "\n" + formatted + "\n" + formattedDate + "/us-east-1/s3/aws4_request"
				+ "\n"
				+ sha256;
		this.signature = Hex.encodeHexString(hmacSHA256(signingKey, stringToSign));
		System.out.println(canonicalRequest);

		HttpHeaders headers = new HttpHeaders();
		String authHeader = "AWS4-HMAC-SHA256" + " " + "Credential=" + this.sid
				+ "/" + formattedDate
				+ "/us-east-1/s3/aws4_request," //
				+
				"SignedHeaders=host;x-amz-date;x-amz-content-sha256," +
				"Signature=" + this.signature;
		headers.add("Authorization", authHeader);
		headers.add("x-amz-date", formatted);
		headers.add("x-amz-content-sha256", "UNSIGNED-PAYLOAD");
		headers.add("Host", this.host);
		HttpEntity<?> req = new HttpEntity<>(null, headers);
		RestTemplate r = new RestTemplate();
		ResponseEntity<byte[]> result = r.exchange(this.uri + "/shop-next/product_images/" + imgname,
				HttpMethod.GET, req,
				byte[].class);
		System.out.println(result.getBody());
		return result;
	}
}
