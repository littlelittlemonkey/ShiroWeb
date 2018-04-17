package com.lyc.utils;

import com.lyc.entity.User;

import java.io.*;

/**
 * 序列化，反序列化工具
 */
public class SerializeUtil {
    static final Class<?> CLAZZ = SerializeUtil.class;

    public static byte[] serialize(Object object) {
        //ObjectOutputStream 将 Java 对象的基本数据类型和图形写入 OutputStream
        ObjectOutputStream oos = null;
        //此类实现了一个输出流，其中的数据被写入一个 byte 数组。缓冲区会随着数据的不断写入而自动增长
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            // 将指定的对象写入 ObjectOutputStream。
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            close(baos);
            close(oos);
        }
        return null;
    }


    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    public static <T> T deserialize(byte[] in, Class<T>...requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            System.out.println("反序列化失败："+e.getMessage());
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                System.out.println("close stream error："+e.getMessage());
            }
    }

    public static void main(String[] args) {
        User user  = new User();
        user.setPassword("as");
        user.setUserName("as");
        byte[] u = SerializeUtil.serialize(user);
        System.out.println(u);
        User uu = SerializeUtil.deserialize(u,User.class);
        System.out.println(uu);
    }
}
