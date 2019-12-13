package org.fisco.bcos.asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Bill extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b506000336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506305f5e100600260008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555060016000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050600173ca35b7d915458ef540ade6068dfe2f44e8fa733c90806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505060017314723a09acff6d2a60dcdf7aa4aff308fddc160c90806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506001734b0897b0513fdc7c541b6d9d7e929c4e5364d2db90806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050600460a0604051908101604052806127108152602001620186a081526020016001808154811015156102df57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff021916908315150217905550505050600460a060405190810160405280614e208152602001620186a0815260200160018081548110151561048757fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600160028154811015156104dd57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff021916908315150217905550505050600190505b60048110156106b45761c3506002600060018481548110151561063e57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550808060010191505061061f565b50611dcc80620006c56000396000f3006080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329e015e6146100b457806348376bf41461012157806376cdb03b1461015857806385c1e683146101af57806397125c5a1461020657806399215c64146102735780639b96eece1461032c578063a40b28a814610383578063e1c7392a1461039a578063f2a40db8146103b1578063ff7dfa2d1461041e575b600080fd5b3480156100c057600080fd5b506100df60048036038101908080359060200190929190505050610449565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561012d57600080fd5b50610156600480360381019080803590602001909291908035906020019092919050505061048c565b005b34801561016457600080fd5b5061016d6109ff565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156101bb57600080fd5b50610204600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919080359060200190929190505050610a24565b005b34801561021257600080fd5b50610271600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610fb8565b005b34801561027f57600080fd5b5061029e600480360381019080803590602001909291905050506117b4565b604051808681526020018581526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001821515151581526020019550505050505060405180910390f35b34801561033857600080fd5b5061036d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611846565b6040518082815260200191505060405180910390f35b34801561038f57600080fd5b5061039861188f565b005b3480156103a657600080fd5b506103af611ada565b005b3480156103bd57600080fd5b506103dc60048036038101908080359060200190929190505050611b22565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561042a57600080fd5b50610433611b60565b6040518082815260200191505060405180910390f35b600060018281548110151561045a57fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60008060003373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515610556576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f53656c662d66756e646520697320646973616c6c6f7765642e0000000000000081525060200191505060405180910390fd5b6000851115156105f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001807f5468652076616c7565206e65656420746f20626967676572207468616e207a6581526020017f726f00000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600084111515610692576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001807f546865206c61737454696d65206e65656420746f20626967676572207468616e81526020017f207a65726f00000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b6000925060009150600090505b60048054905081101561097c573373ffffffffffffffffffffffffffffffffffffffff166004828154811015156106d257fe5b906000526020600020906004020160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614801561075857506000151560048281548110151561073557fe5b906000526020600020906004020160030160149054906101000a900460ff161515145b1561096f5760048181548110151561076c57fe5b90600052602060002090600402016000015482019150848210151561096e57600460a06040519081016040528087815260200186420181526020013373ffffffffffffffffffffffffffffffffffffffff1681526020016000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff0219169083151502179055505050506109236000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff163387611b6d565b50600192507f5795b999c64a50dec9f8636d1bb9dc66356d04c7c6dafeb64c4c828abfd2600985854201604051808381526020018281526020019250505060405180910390a161097c565b5b808060010191505061069f565b600115158315151415156109f8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f6170706c79696e6720666f722066756e6465206661696c65640000000000000081525060200191505060405180910390fd5b5050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6001600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555060011515600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff161515141515610b70576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f4f6e6c792067657474696e672072696768742066726f6d2062616e6b2063616e81526020017f207472616465000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614151515610c14576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f53656c662d74726164657220697320646973616c6c6f7765642e00000000000081525060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614158015610c52575060008210155b1515610cc6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4e6f742076616c69642061646472657373206f722076616c756500000000000081525060200191505060405180910390fd5b600081111515610d64576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001807f546865206c61737454696d65206e65656420746f20626967676572207468616e81526020017f207a65726f00000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600460a06040519081016040528084815260200183420181526020013373ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff0219169083151502179055505050506000600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff0219169083151502179055507fa326259ec721617acd3cb2a00bcbeac91eefe409880e49aa2bbf473ed648da49338484844201604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182815260200194505050505060405180910390a1505050565b6000806001600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff02191690831515021790555060011515600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900460ff161515141515611107576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f4f6e6c792067657474696e672072696768742066726f6d2062616e6b2063616e81526020017f207472616465000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b3373ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff16141515156111ab576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f53656c662d74726164657220697320646973616c6c6f7765642e00000000000081525060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614151515611250576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f4e6f742076616c6964206164647265737300000000000000000000000000000081525060200191505060405180910390fd5b6000831115156112ee576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001807f5468652076616c7565206e65656420746f20626967676572207468616e207a6581526020017f726f00000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b60009150600090505b600480549050811015611731578473ffffffffffffffffffffffffffffffffffffffff1660048281548110151561132a57fe5b906000526020600020906004020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161480156113ea57503373ffffffffffffffffffffffffffffffffffffffff166004828154811015156113a057fe5b906000526020600020906004020160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b801561142757506000151560048281548110151561140457fe5b906000526020600020906004020160030160149054906101000a900460ff161515145b15611724578260048281548110151561143c57fe5b90600052602060002090600402016000015410151561172357600460a06040519081016040528085815260200160048481548110151561147857fe5b90600052602060002090600402016001015481526020018773ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1681526020016000151581525090806001815401808255809150509060018203906000526020600020906004020160009091929091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060608201518160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060808201518160030160146101000a81548160ff021916908315150217905550505050826004828154811015156115ce57fe5b906000526020600020906004020160000160008282540392505081905550600191506000600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548160ff0219169083151502179055507fae3682de1c38196b00584dddc0712f94bf7e1ff30113bc8d48cb28f04c584e7c85338686604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200194505050505060405180910390a1611731565b5b80806001019150506112f7565b600115158215151415156117ad576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f7472616e73666572696e672042696c6c206661696c656400000000000000000081525060200191505060405180910390fd5b5050505050565b6004818154811015156117c357fe5b90600052602060002090600402016000915090508060000154908060010154908060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060030160149054906101000a900460ff16905085565b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b60008090505b6004805490508160ff161015611ad75760048160ff168154811015156118b757fe5b906000526020600020906004020160010154421015801561190c57506000151560048260ff168154811015156118e957fe5b906000526020600020906004020160030160149054906101000a900460ff161515145b151561191757600080fd5b600060048260ff1681548110151561192b57fe5b906000526020600020906004020160000154101515156119d9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001807f546869732042696c6c7320256427732076616c7565206973206e6f742076616c81526020017f696400000000000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600160048260ff168154811015156119ed57fe5b906000526020600020906004020160030160146101000a81548160ff021916908315150217905550611ac960048260ff16815481101515611a2a57fe5b906000526020600020906004020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660048360ff16815481101515611a6e57fe5b906000526020600020906004020160030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660048460ff16815481101515611ab257fe5b906000526020600020906004020160000154611b6d565b508080600101915050611895565b50565b61c350600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550565b600181815481101515611b3157fe5b906000526020600020016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600480549050905090565b6000611b7a848484611c24565b7fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef848484604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828152602001935050505060405180910390a1600190509392505050565b80600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515611c7257600080fd5b600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205481600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110151515611d0157600080fd5b80600260008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555080600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505050505600a165627a7a72305820ecc840822f775ba170ac6e26bbc0457dd6edec741797c2c70776337cb04c96c10029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"showAccount\",\"outputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_value\",\"type\":\"uint256\"},{\"name\":\"_lastTime\",\"type\":\"uint256\"}],\"name\":\"applyForFunde\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"bank\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_seller\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"},{\"name\":\"_lastTime\",\"type\":\"uint256\"}],\"name\":\"purchaseAndMakeBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_rootCompany\",\"type\":\"address\"},{\"name\":\"_receiver\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"Bills\",\"outputs\":[{\"name\":\"value\",\"type\":\"uint256\"},{\"name\":\"billEndTime\",\"type\":\"uint256\"},{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"isOvered\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"tokenOwner\",\"type\":\"address\"}],\"name\":\"getBalanceOf\",\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"autoPayForBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"init\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"accounts\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getBillNum\",\"outputs\":[{\"name\":\"num\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"purchaser\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"value\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"billEndTime\",\"type\":\"uint256\"}],\"name\":\"Purchased\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"rootCompany\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"purchaser\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"seller\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"BillTransformed\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"value\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"billEndTime\",\"type\":\"uint256\"}],\"name\":\"FundeApplied\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"from\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"}]";

    public static final String FUNC_SHOWACCOUNT = "showAccount";

    public static final String FUNC_APPLYFORFUNDE = "applyForFunde";

    public static final String FUNC_BANK = "bank";

    public static final String FUNC_PURCHASEANDMAKEBILL = "purchaseAndMakeBill";

    public static final String FUNC_TRANSFERBILL = "transferBill";

    public static final String FUNC_BILLS = "Bills";

    public static final String FUNC_GETBALANCEOF = "getBalanceOf";

    public static final String FUNC_AUTOPAYFORBILL = "autoPayForBill";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_ACCOUNTS = "accounts";

    public static final String FUNC_GETBILLNUM = "getBillNum";

    public static final Event PURCHASED_EVENT = new Event("Purchased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BILLTRANSFORMED_EVENT = new Event("BillTransformed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDEAPPLIED_EVENT = new Event("FundeApplied", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Bill(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Bill(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Bill(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Bill(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> showAccount(BigInteger i) {
        final Function function = new Function(FUNC_SHOWACCOUNT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(i)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> applyForFunde(BigInteger _value, BigInteger _lastTime) {
        final Function function = new Function(
                FUNC_APPLYFORFUNDE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void applyForFunde(BigInteger _value, BigInteger _lastTime, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_APPLYFORFUNDE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String applyForFundeSeq(BigInteger _value, BigInteger _lastTime) {
        final Function function = new Function(
                FUNC_APPLYFORFUNDE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> bank() {
        final Function function = new Function(FUNC_BANK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> purchaseAndMakeBill(String _seller, BigInteger _value, BigInteger _lastTime) {
        final Function function = new Function(
                FUNC_PURCHASEANDMAKEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_seller), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void purchaseAndMakeBill(String _seller, BigInteger _value, BigInteger _lastTime, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PURCHASEANDMAKEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_seller), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String purchaseAndMakeBillSeq(String _seller, BigInteger _value, BigInteger _lastTime) {
        final Function function = new Function(
                FUNC_PURCHASEANDMAKEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_seller), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_lastTime)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> transferBill(String _rootCompany, String _receiver, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_rootCompany), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferBill(String _rootCompany, String _receiver, BigInteger _value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_rootCompany), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferBillSeq(String _rootCompany, String _receiver, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_rootCompany), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple5<BigInteger, BigInteger, String, String, Boolean>> Bills(BigInteger param0) {
        final Function function = new Function(FUNC_BILLS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple5<BigInteger, BigInteger, String, String, Boolean>>(
                new Callable<Tuple5<BigInteger, BigInteger, String, String, Boolean>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, String, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getBalanceOf(String tokenOwner) {
        final Function function = new Function(FUNC_GETBALANCEOF, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(tokenOwner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> autoPayForBill() {
        final Function function = new Function(
                FUNC_AUTOPAYFORBILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void autoPayForBill(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_AUTOPAYFORBILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String autoPayForBillSeq() {
        final Function function = new Function(
                FUNC_AUTOPAYFORBILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> init() {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void init(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String initSeq() {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> accounts(BigInteger param0) {
        final Function function = new Function(FUNC_ACCOUNTS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getBillNum() {
        final Function function = new Function(FUNC_GETBILLNUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<PurchasedEventResponse> getPurchasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PURCHASED_EVENT, transactionReceipt);
        ArrayList<PurchasedEventResponse> responses = new ArrayList<PurchasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PurchasedEventResponse typedResponse = new PurchasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.purchaser = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.billEndTime = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerPurchasedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PURCHASED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerPurchasedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PURCHASED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<BillTransformedEventResponse> getBillTransformedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BILLTRANSFORMED_EVENT, transactionReceipt);
        ArrayList<BillTransformedEventResponse> responses = new ArrayList<BillTransformedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BillTransformedEventResponse typedResponse = new BillTransformedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rootCompany = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.purchaser = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerBillTransformedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(BILLTRANSFORMED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerBillTransformedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(BILLTRANSFORMED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<FundeAppliedEventResponse> getFundeAppliedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDEAPPLIED_EVENT, transactionReceipt);
        ArrayList<FundeAppliedEventResponse> responses = new ArrayList<FundeAppliedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundeAppliedEventResponse typedResponse = new FundeAppliedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.billEndTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerFundeAppliedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FUNDEAPPLIED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerFundeAppliedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FUNDEAPPLIED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTransferEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFER_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerTransferEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFER_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static Bill load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bill(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Bill load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bill(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Bill load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Bill(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Bill load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Bill(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Bill> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Bill.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Bill> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Bill.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Bill> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Bill.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Bill> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Bill.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class PurchasedEventResponse {
        public Log log;

        public String purchaser;

        public String seller;

        public BigInteger value;

        public BigInteger billEndTime;
    }

    public static class BillTransformedEventResponse {
        public Log log;

        public String rootCompany;

        public String purchaser;

        public String seller;

        public BigInteger value;
    }

    public static class FundeAppliedEventResponse {
        public Log log;

        public BigInteger value;

        public BigInteger billEndTime;
    }

    public static class TransferEventResponse {
        public Log log;

        public String from;

        public String to;

        public BigInteger amount;
    }
}
