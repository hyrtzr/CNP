package com.jingdong.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encrypt
{
	
	private static final char DIGITS[];

    static 
    {
        char ac[] = new char[16];
        ac[0] = '0';
        ac[1] = '1';
        ac[2] = '2';
        ac[3] = '3';
        ac[4] = '4';
        ac[5] = '5';
        ac[6] = '6';
        ac[7] = '7';
        ac[8] = '8';
        ac[9] = '9';
        ac[10] = 'a';
        ac[11] = 'b';
        ac[12] = 'c';
        ac[13] = 'd';
        ac[14] = 'e';
        ac[15] = 'f';
        DIGITS = ac;
    }

    public Md5Encrypt()
    {
    }

    public static char[] encodeHex(byte abyte0[])
    {
        int i = abyte0.length;
        char ac[] = new char[i << 1];
        int j = 0;
        int k = 0;
        do
        {
            if(j >= i)
                return ac;
            int l = k + 1;
            ac[k] = DIGITS[(0xf0 & abyte0[j]) >>> 4];
            k = l + 1;
            ac[l] = DIGITS[0xf & abyte0[j]];
            j++;
        } while(true);
    }
    
    public static byte[] decodeHex(char[] data) {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch
					+ " at index " + index);
		}
		return digit;
	}

    public static String md5(String s)
    {
        MessageDigest messagedigest;
        try
        {
            messagedigest = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        try
        {
            messagedigest.update(s.getBytes("utf-8"));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new IllegalStateException("System doesn't support your  EncodingException.");
        }
        return new String(encodeHex(messagedigest.digest()));
    }

}
