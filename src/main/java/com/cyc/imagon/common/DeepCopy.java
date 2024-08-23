package com.cyc.imagon.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 该类实现图像的深拷贝
 *
 * @author 申雄全
 * Date 2023/12/24 1:51
 */
public class DeepCopy {
    public static BufferedImage cpyBufferedImage(BufferedImage source) {
        if (source == null)
            return null;
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;

    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T src) {
        T cloneObj = null;
        try {
            ObjectInputStream ois = getInputStream(src);
            cloneObj = (T) ois.readObject();//返回生成的新对象
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }


    public static <T> List<T> deepCopy(List<T> src) {
        List<T> dest = null;
        try {
            ObjectInputStream ois = getInputStream(src);
            dest = (List<T>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dest;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> cloneMap(Map<K, V> src) throws IOException, ClassNotFoundException {
        Map<K, V> result = null;
        try {
            ObjectInputStream ois = getInputStream(src);
            result = (Map<K, V>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static ObjectInputStream getInputStream(Object src) throws IOException, ClassNotFoundException {
        //写入字节流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream obs = new ObjectOutputStream(out);
        obs.writeObject(src);
        obs.close();
        //分配内存，写入原始对象，生成新对象
        ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
        return new ObjectInputStream(ios);
    }

    public static <T extends Serializable> T clones(T shape1) {
        T cloneObj = null;
        try {
            ObjectInputStream ois = getInputStream(shape1);
            cloneObj = (T) ois.readObject();//返回生成的新对象
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }


}
