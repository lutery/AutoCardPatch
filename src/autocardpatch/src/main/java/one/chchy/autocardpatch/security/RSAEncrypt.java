package one.chchy.autocardpatch.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import one.chchy.autocardpatch.util.GeneraUtil;

    public class RSAEncrypt {
        private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

        public static String rsaE = "010001";
        public static String rsaM = "868B1E4CB4E2F3E06058C9EA434F5E9437B8E9E2F53F8F74A943E0723D5FB5D17A33AF93B506464E50AAD5061AA488767C386E185DA114B38B9ED6F2308C7B44429BDBCCC7E3C58B7E744B375AF21DCD1E1A905158EEAD75D490009FA9E7D800D677943DCD10F996FE3BAF2E77E4B4330185666B46378E8BEE2C9422304BF43B";
        public static String randSeed = "168e8914-4a9e-472e-b407-d1dd768cb705";
        public static String userName = "chenghui";
        public static String password = "0147258369qQ_";
        public static String platform = "1";
        public static String passport = "1b2b289de7aa8aba11f5ce9076505af9ff7e5ad51713d8d05b6594a388acba91a758e236a72160b56af71e4bd0ec836b0101d62c01fffbe7bf6fa3a06ea1cab5b32aa55b9e3724051452802832718e1069b5048f34594718b97e2625cdbedebf0e9b39b4cdad1159580c500dc2d39d3645b8d1c56908138683d66c1c726b7dc6";

        public static PublicKey genPublicKey(String publicExponent, String modulus) throws NoSuchAlgorithmException, InvalidKeySpecException {
            BigInteger bigIntModulus = new BigInteger(modulus,16);

            BigInteger bigIntPrivateExponent = new BigInteger(publicExponent,16);

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            return publicKey;
        }

        /**
         * RSA公钥加密
         *
         * @param str
         *            加密字符串
         * @param publicKey
         *            公钥
         * @return 密文
         * @throws Exception
         *             加密过程中的异常信息
         */
        public static String encrypt( String str, PublicKey publicKey ) throws Exception{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return GeneraUtil.byte2HexStr(cipher.doFinal(str.getBytes("utf-8")));
        }

        public static String encrypt(String str) throws Exception {
            System.out.println(str);

            return RSAEncrypt.encrypt(str, genPublicKey(RSAEncrypt.rsaE, rsaM));
        }

        public static String encode64Str(String userName, String password, String randSeed, String platform) throws UnsupportedEncodingException {
            return Base64.encodeBase64String(userName.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(password.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(randSeed.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(platform.getBytes("utf-8"));
        }

        public static void main(String[] args) throws Exception {
//            PublicKey publicKey = genPublicKey(RSAEncrypt.rsaE, rsaM);
            //加密字符串
            String toEncodeStr = Base64.encodeBase64String(userName.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(password.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(randSeed.getBytes("utf-8")) + "/" +
                    Base64.encodeBase64String(platform.getBytes("utf-8"));
            System.out.println("toEncode str is " + toEncodeStr);
            System.out.println(encrypt(toEncodeStr));
        }

    }

