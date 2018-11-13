package com.client.utils;


import org.apache.commons.lang3.StringUtils;
import org.spongycastle.util.encoders.Hex;
import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by silentalk on 2018/1/25.
 */

public class Accountant {

    private  static final Long BTC = 1_00_000_000L;
    private  static final Long ETH = 1_000_000_000_000_000_000L;

    public static final BigDecimal MAX_BTC = new BigDecimal(Long.toString(21_000_000L));
    public static final BigDecimal MAX_SATOSHI = new BigDecimal(Long.toString(2100_000_000_000_000L));

    public static final BigDecimal DUST_VALUE = new BigDecimal("5000");
    public static final BigDecimal GAS_MIN_BTC = new BigDecimal("0");
    public static final BigDecimal GAS_DEFAULT_BTC = new BigDecimal(Long.toString(200_000L));
    public static final BigDecimal GAS_MAX_BTC = new BigDecimal(Long.toString(10_000_000L));

    public static final BigDecimal GAS_PRICE_MIN_ETH = new BigDecimal(Long.toString(1_000_000_000L)); // 1 Gwei
    public static final BigDecimal GAS_PRICE_DEFAULT_ETH = new BigDecimal(Long.toString(25_200_000_000L)); // 25.2 Gwei
    public static final BigDecimal GAS_PRICE_MAX_ETH = new BigDecimal(Long.toString(120_000_000_000L)); // 120 Gwei

    public static final BigDecimal GAS_LIMIT_DEFAULT_ETH = new BigDecimal("21000");
    public static final BigDecimal GAS_LIMIT_DEFAULT_ERC20 = new BigDecimal(Long.toString(2000_000L));

    public static BigDecimal scaleBtc(BigDecimal bigDecimal) {
        return bigDecimal.setScale(8, BigDecimal.ROUND_DOWN);
    }
    public static BigDecimal scaleEth(BigDecimal bigDecimal) {
        return bigDecimal.setScale(18, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal divide(BigDecimal from, BigDecimal by, int scale) {
        if (BigDecimal.ZERO.compareTo(from) == 0) return BigDecimal.ZERO;
        return from.divide(by, scale, BigDecimal.ROUND_DOWN);
    }
//    public static BigDecimal divide(BigDecimal from, BigDecimal by) {
//        return from.divide(by, 50, BigDecimal.ROUND_DOWN);
//    }

    public static boolean isBtcString(String str) {
        if (!isDecimal(str)) return false;
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(str);
        } catch (Exception e) {
            return false;
        }
        if (bigDecimal.compareTo(MAX_BTC) > 0) return false;
        return true;
    }

    public static boolean isEthString(String str) {
        if (!isDecimal(str)) return false;
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static BigDecimal toSmallest(BigDecimal bigDecimal, BigInteger decimals) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) return BigDecimal.ZERO;
        int scale = decimals.intValue();
        return bigDecimal.scaleByPowerOfTen(scale);
    }
    public static BigDecimal toReadable(BigDecimal bigDecimal, BigInteger decimals) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) return BigDecimal.ZERO;
    	int scale = decimals.intValue();
        return bigDecimal.scaleByPowerOfTen(-scale);
    }
    public static String stripZeros(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) return "0";
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    public static BigDecimal btc2satoshi(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) return BigDecimal.ZERO;
        BigDecimal satoshi = bigDecimal.multiply(new BigDecimal(BTC.toString()));
        return satoshi;
    }

    public static BigDecimal eth2wei(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) return BigDecimal.ZERO;
        BigDecimal wei = bigDecimal.multiply(new BigDecimal(ETH.toString()));
        return wei;
    }

    public static BigDecimal satoshi2btc(BigDecimal bigDecimal) {
        BigDecimal btc = divide(bigDecimal, new BigDecimal(BTC.toString()), 8);
        return scaleBtc(btc);
    }

    public static BigDecimal satoshi2btc(BigInteger bigInteger) {
        if (BigInteger.ZERO.compareTo(bigInteger) == 0) return BigDecimal.ZERO;
        BigDecimal btc = divide(new BigDecimal(bigInteger), new BigDecimal(BTC.toString()), 8);
        return scaleBtc(btc);
    }

    public static BigDecimal wei2eth(BigDecimal bigDecimal) {
        BigDecimal eth = divide(bigDecimal, new BigDecimal(ETH.toString()), 18);
        return scaleEth(eth);
    }

    public static BigDecimal wei2eth(BigInteger bigInteger) {
        BigDecimal eth = divide(new BigDecimal(bigInteger), new BigDecimal(ETH.toString()), 18);
        return scaleEth(eth);
    }



    //035216e8f6f908ab06f6605fc1912d8ee25106a6
    public static boolean isEthereumAddress(String candidate) {
        if (candidate == null) return false;
        try {
            Address address = new Address(candidate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMnemonicCode(String mnemonic) {
        if (mnemonic == null || mnemonic.length() == 0) return false;

        String all = null;
        String[] words = null;
        try {
            all = mnemonic.trim();
            all = all.replaceFirst(" +", " ");
            words = all.split(" ");
        } catch (Exception e) {
            return false;
        }
        if (words == null || words.length != 12) {
            return false;
        }

        return true;
    }
    
    public static boolean isDecimal(String str) {
		if (str == null) {
			return false;
		}
		if (isNumeric(str)) return true;
		Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
    
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static String[] splitMnemonic(String mnemonic) {
        try {
            mnemonic = mnemonic.trim();
            mnemonic = mnemonic.replaceAll("\\s+", " ");
            String[] words = mnemonic.split(" ");
            if (words.length != 12) return null;
            return words;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String mnemonicToString(List<String> seed) {
        if (seed == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (String word : seed) {
            sb.append(word).append(" ");
        }
        // Remove the extraneous space character
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String shapeEthAddress(String address) {
        if (StringUtils.isEmpty(address)) return "";
        String shapedAddress = Numeric.prependHexPrefix(address);
        return shapedAddress.toLowerCase();
    }
    public static String shapeEthDisplayAddress(String address) {
        if (StringUtils.isEmpty(address)) return "";
        String shapedAddress = Numeric.cleanHexPrefix(address);
        return shapedAddress.toLowerCase();
    }
    public static String shapeEthTokenAddress(String tokenAddress) {
        return shapeEthAddress(tokenAddress);
    }
    public static String shapeEthPrivateKey(String privateKeyHex) {
        return shapeEthAddress(privateKeyHex);
    }
    public static String shapeEthSignedTx(String signedTxHex) {
        return shapeEthAddress(signedTxHex);
    }

    public static String shrinkEthAddress(String ethAddress) {
        ethAddress = shapeEthDisplayAddress(ethAddress);
        String head = ethAddress.substring(0,6);
        String body = "......";
        String tail = ethAddress.substring(ethAddress.length()-6);
        return head+body+tail;
    }
}
