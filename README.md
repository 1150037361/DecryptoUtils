# DecryptoUtils
  burp解密插件
  能够在发包窗口实时加解密相关字段数据，可选择全部或者JSON内的字段，都能够选取，以应对越来越多的加密测试环境，能够支持AES，DES，3DES，RSA，SM2，SM4，以及相关JS扩展，同时内置crypto-js库能够很好的适配JS加密环境

---

# 详细功能使用

### 1.主要功能页面
  在重放器页面能够灵活加解密字段，便于测试

---
![img](https://github.com/1150037361/DecryptoUtils/blob/master/img/%E4%BD%BF%E7%94%A8%E7%95%8C%E9%9D%A2.png)


### 2. 使用界面的解密配置
  使用界面的加解密配置，配置相关加解密方式，以及密钥等信息（使用JS扩展无需配置密钥，在代码中定义即可）
  
---
![img](https://github.com/1150037361/DecryptoUtils/blob/master/img/%E8%A7%A3%E5%AF%86%E9%85%8D%E7%BD%AE.png)


### 3. JS扩展配置
  配置JS加密的独立页面，内置crypto-js库，全局名称为CryptoJS，以下为示例代码和页面图片（代码只能使用var来定义相关参数，其他的JS引擎不支持，重新加载插件是无法编辑代码，只能够右键直接粘贴编写好的代码，BUG问题实在找不出来）
  
---
``` JavaScript
 function wxEncryptData (word) {
    var key = CryptoJS.SHA1(CryptoJS.SHA1("114514")).toString().substring(0, 32);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, CryptoJS.enc.Hex.parse(key), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    })
    return encrypted.ciphertext.toString().toUpperCase();
}

function wxDecryptData (data) {
    var wordArray = CryptoJS.enc.Hex.parse(data)
    var base64Word = CryptoJS.enc.Base64.stringify(wordArray)
    var key = CryptoJS.SHA1(CryptoJS.SHA1("114514")).toString().substring(0, 32);
    var encrypted = CryptoJS.AES.decrypt(base64Word, CryptoJS.enc.Hex.parse(key), {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    })
    return CryptoJS.enc.Utf8.stringify(encrypted).toString()
};
```

![img](https://github.com/1150037361/DecryptoUtils/blob/master/img/%E6%89%A9%E5%B1%95%E9%85%8D%E7%BD%AE.png)



### 4.独立加解密页面
  配置好相关密钥信息，即可进行加解密

---
![img](https://github.com/1150037361/DecryptoUtils/blob/master/img/%E8%A7%A3%E7%A0%81%E5%B7%A5%E5%85%B7.png)  

