package com.client.sdk.dto.eth;


import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.tx.ChainId;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;

public class BGETHTXHelper {

    /**
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @return
     */
    public static RawTransaction createEthTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value) {

        return RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
    }

    /**
     * 创建ERC20转账
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param tokenAddress
     * @param to
     * @param value
     * @return
     */
    public static RawTransaction createErc20Transaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String tokenAddress
    ) {
        //先编码
        String data = encodeTransferEvent(value, to);
        //
        tokenAddress = Numeric.prependHexPrefix(tokenAddress);
        return RawTransaction.createTransaction(nonce, gasPrice, gasLimit, tokenAddress, data);
    }

    /**
     * @param transaction
     * @param chainId     ChainId.MAINNET 为主链
     * @param credentials
     * @return 返回签名后结果
     */
    public static String signTransaction(RawTransaction transaction, byte chainId, Credentials credentials) {
        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(transaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(transaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        return hexValue;
    }

    public static String encodeTransferEvent(BigInteger value, String receiveAddress) {

        Address _receiveAddress = new Address(receiveAddress);
        Uint256 _value = new Uint256(value);

        Function function = new Function("transfer", Arrays.<Type>asList(_receiveAddress, _value),
                Arrays.<TypeReference<?>>asList(new TypeReference<Type>() {})/*Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {})*/);

        return FunctionEncoder.encode(function);
    }
}
