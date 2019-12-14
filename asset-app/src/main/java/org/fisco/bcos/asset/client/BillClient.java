package org.fisco.bcos.asset.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fisco.bcos.asset.contract.Bill;
import org.fisco.bcos.asset.contract.Bill.PurchasedEventResponse;
import org.fisco.bcos.asset.contract.Bill.BillTransformedEventResponse;
import org.fisco.bcos.asset.contract.Bill.FundeAppliedEventResponse;
import org.fisco.bcos.asset.contract.Bill.TransferEventResponse;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BillClient {

	static Logger logger = LoggerFactory.getLogger(BillClient.class);

	private Web3j web3j;

	private Credentials credentials;

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void recordBillAddr(String address) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("address", address);
		final Resource contractResource = new ClassPathResource("contract.properties");
		FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
		prop.store(fileOutputStream, "contract address");
	}

	public String loadBillAddr() throws Exception {
		// load Bill contact address from contract.properties
		Properties prop = new Properties();
		final Resource contractResource = new ClassPathResource("contract.properties");
		prop.load(contractResource.getInputStream());

		String contractAddress = prop.getProperty("address");
		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw new Exception(" load Bill contract address failed, please deploy it first. ");
		}
		logger.info(" load Bill address from contract.properties, address is {}", contractAddress);
		return contractAddress;
	}

	public void initialize() throws Exception {

		// init the Service
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
		service.run();

		ChannelEthereumService channelEthereumService = new ChannelEthereumService();
		channelEthereumService.setChannelService(service);
		//init web3
		Web3j web3j = Web3j.build(channelEthereumService, 1);

		// init Credentials
		Credentials credentials = Credentials.create(Keys.createEcKeyPair());

		setCredentials(credentials);
		setWeb3j(web3j);

		logger.debug(" web3j is " + web3j + " ,credentials is " + credentials);
	}

	private static BigInteger gasPrice = new BigInteger("30000000");
	private static BigInteger gasLimit = new BigInteger("30000000");




	public void deployBillAndRecordAddr() {

		try {
			Bill bill = Bill.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
			System.out.println(" deploy Bill success, contract address is " + bill.getContractAddress());

			recordBillAddr(bill.getContractAddress());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" deploy Bill contract failed, error message is  " + e.getMessage());
		}
	}
	public void initBalance(){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			bill.init();
			System.out.println("initBalance succcess, the balance is 50000");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" init balance failed, error message is  " + e.getMessage());
		}
	}
	public void balanceOf(String account){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			BigInteger result = bill.getBalanceOf(account).send();
			System.out.printf("account %s, balance %s \n", account, result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" get balance failed, error message is  " + e.getMessage());
		}
	}
	public void showAccount(BigInteger i){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			String result = bill.showAccount(i).send();
			System.out.printf("show account %s, address %s \n", i, result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" show account failed, error message is  " + e.getMessage());
		}
	}

	public void bank(){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			String result = bill.bank().send();
			System.out.printf("bank, address %s \n",result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" get bank address failed, error message is  " + e.getMessage());
		}
	}

	public void getBillNum(){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			BigInteger result = bill.getBillNum().send();
			System.out.printf("BillsNum : %s\n",result);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getBillNum failed, error message is  " + e.getMessage());
		}
	}
	public void Bills(BigInteger i){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			Tuple5<BigInteger, BigInteger, String, String, Boolean> result = bill.Bills(i).send();
			System.out.printf("Bills %s: value %s, EndTime: %s,from: %s, to: %s, isOvered:%s\n", i, result.getValue1(),result.getValue2(),result.getValue3(),result.getValue4(),result.getValue5());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" show bills failed, error message is  " + e.getMessage());
		}
	}

	public void purchase(String _seller, BigInteger _value, BigInteger _lastTime){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			TransactionReceipt receipt = bill.purchaseAndMakeBill(_seller,_value,_lastTime).send();
			System.out.printf("purchase success \n");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("purchase failed, error message is  " + e.getMessage());
		}
}
	public void transfer(String _rootCompany, String _receiver, BigInteger _value){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			TransactionReceipt receipt = bill.transferBill(_rootCompany, _receiver, _value).send();
			System.out.printf("transfer success \n");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("transfer failed, error message is  " + e.getMessage());
		}
}
	public void applyFunde(BigInteger _value, BigInteger _lastTime){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			TransactionReceipt receipt = bill.applyForFunde(_value,_lastTime).send();
			System.out.printf("apply funde success \n");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("apply for funde failed, error message is  " + e.getMessage());
		}
}

	public void autoPay(){
		try {
			String contractAddress = loadBillAddr();
			Bill bill = Bill.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

			bill.autoPayForBill().send();
			System.out.printf("autoPay success \n");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("autoPay failed, error message is  " + e.getMessage());
		}
}
	
	public static void Usage() {
		System.out.println(" Usage:");
		System.out.println("\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient deploy");
		System.out.println("\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient init");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient getBalanceOf account");

		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient showAccount i");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient bank");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient Bills i");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient getBillNum");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient autoPayBill");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient purchaseAndMakeBill sellerAddr value lastingTime");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient transferBill rootCompany seller value");
		System.out.println(
				"\t java -cp conf/:lib/*:apps/* org.fisco.bcos.bill.client.BillClient applyForFunde value lastingTime");
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			Usage();
		}

		BillClient client = new BillClient();
		client.initialize();

		switch (args[0]) {
		case "deploy":
			client.deployBillAndRecordAddr();
			break;
		case "init":
			client.initBalance();
			break;
		case "getBalanceOf":
			if (args.length < 2) {
				Usage();
			}
			client.balanceOf(args[1]);
			break;
		case "showAccount":
			if (args.length < 2) {
				Usage();
			}
			BigInteger i = new BigInteger(args[1]);
			client.showAccount(i);
			break;
		
		case "bank":
			if (args.length < 1) {
				Usage();
			}
			client.bank();
			break;
		case "getBillNum":
			if(args.length < 1){
				Usage();
			}
			client.getBillNum();
			break;
		case "Bills":
			if (args.length < 2) {
				Usage();
			}
			i = new BigInteger(args[1]);
			client.Bills(i);
			break;

		case "autoPayBill":
			client.autoPay();
			break;
		case "purchaseAndMakeBill":
			if (args.length < 4) {
				Usage();
			}
			BigInteger value = new BigInteger(args[2]);
			BigInteger lastTime = new BigInteger(args[3]);
			client.purchase(args[1],value,lastTime);
			break;
		case "transferBill":
			if (args.length < 4) {
				Usage();
			}
			value = new BigInteger(args[3]);
			client.transfer(args[1],args[2],value);
			break;
		case "applyForFunde":
			if (args.length < 3) {
				Usage();
			}
			value = new BigInteger(args[1]);
			lastTime = new BigInteger(args[2]);
			client.applyFunde(value,lastTime);
			break;

		default: {
			Usage();
		}
		}

		System.exit(0);
	}
}
