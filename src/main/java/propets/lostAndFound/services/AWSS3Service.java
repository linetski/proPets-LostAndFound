package propets.lostAndFound.services;

import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class AWSS3Service {
	
	AmazonS3 s3client;
	AWSCredentials credentials;
	String bucketName = "propets-images";
	
	public AWSS3Service() {
		credentials = new BasicAWSCredentials(
				  "AKIAI7WO2KDFK4BA6YLA", 
				  "bIrSN7ZsHR/rHL4+6X4oaAAqXNbUY67aa8IwJvu/"
				);
		s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.EU_CENTRAL_1)
				  .build();
		System.out.println("AWSS3Service");
		listBuckets();
		
	}
	
	public void listBuckets() {
		List<Bucket> buckets = s3client.listBuckets();
		for(Bucket bucket : buckets) {
		    System.out.println(bucket.getName());
		}
	}
	
	
	/*
	 * public PutObjectResult saveFile(MultipartFile file,String folderName) {
	 * ObjectMetadata om = new ObjectMetadata();
	 * om.setContentLength(file.getSize());
	 * 
	 * try { return s3client.putObject( bucketName, folderName + "/" +
	 * file.getName(), file.getInputStream(), om ); } catch (AmazonServiceException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (SdkClientException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return null; }
	 */
}
