package utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.security.PublicKey;


public class SM2Util {

    public static String Encrypt(String data, String publicKey, String codeType) {
        ECPublicKeyParameters ecPublicKeyParameters = null;
        //这里需要根据公钥的长度进行加工
        if (publicKey.length() == 130) {
            //这里需要去掉开始第一个字节 第一个字节表示标记
            publicKey = publicKey.substring(2);
            String xhex = publicKey.substring(0, 64);
            String yhex = publicKey.substring(64, 128);
            ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        } else {
            PublicKey p = BCUtil.decodeECPoint(publicKey, SmUtil.SM2_CURVE_NAME);
            ecPublicKeyParameters = BCUtil.toParams(p);
        }
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        // 公钥加密
        if (codeType.equals("HEX")) {
            return sm2.encryptHex(data, KeyType.PublicKey);
        } else {
            return sm2.encryptBase64(data, KeyType.PublicKey);
        }
    }

    public static String Decrypt(String data, String privateKey) {
        ECPrivateKeyParameters ecPrivateKeyParameters = BCUtil.toSm2Params(privateKey);
        //创建sm2 对象
        SM2 sm2 = new SM2(ecPrivateKeyParameters, null);
        // 私钥解密
        return StrUtil.utf8Str(sm2.decryptStr(data, KeyType.PrivateKey));
    }
}
