package com.transilink.framework.core.utils.fileUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.utils.fileUtils.fileloader.FileLoader;
import com.transilink.framework.core.utils.fileUtils.fileloader.FileLoaderFactory;
import com.transilink.framework.core.utils.fileUtils.fileloader.PathFileLoader;
import com.transilink.framework.core.utils.zipUtils.CZipEntry;
import com.transilink.framework.core.utils.zipUtils.CZipOutputStream;

/**
 * 
 * 描述： 方便操作文件的类
 * 
 * @author ocean 2015年4月15日 email：zhangjunfang0505@163.com
 */
public class IoUtil {

	private static final String DEFAULT_CHAR_SET = "UTF-8";

	/**
	 * IO 读取缓存
	 */
	public static final int BUFFERSIZE = 4096;

	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * The number of bytes in a gigabyte.
	 */
	public static final long ONE_GB = ONE_KB * ONE_MB;

	/**
	 * The Unix separator character.
	 */
	private static final char UNIX_SEPARATOR = '/';

	/**
	 * The Windows separator character.
	 */
	private static final char WINDOWS_SEPARATOR = '\\';

	private static final Logger log = Logger.getLogger(IoUtil.class);

	private IoUtil() {
		// prevent instantiation
	}

	/**
	 * 创建文件,如果文件不存在;否则,返回文件.
	 *
	 * @param filePath
	 *            文件路径,如果路径为空,返回空.
	 * @return 文件路径对应的文件.
	 * @throws IOException
	 *             发生文件读取/创建失败时抛出.
	 */
	public static File createFile(String filePath) throws IOException {
		if (filePath == null)
			return null;
		File f = new File(filePath);
		if (f.exists()) {
			return f;
		} else {
			File p = f.getParentFile();
			if (!p.exists())
				p.mkdirs();
			f.createNewFile();
			return f;
		}
	}

	/**
	 * 删除文件.如果文件存在返回删除是否成功;否则,返回成功.
	 *
	 * @param filePath
	 *            文件路径,如果路径为空,返回真.
	 * @return 删除是否成功.
	 * @throws IOException
	 *             发生文件读取/创建失败时抛出.
	 */
	public static boolean deleteFile(String filePath) throws IOException {
		if (filePath == null)
			return true;
		File f = new File(filePath);
		if (f.exists()) {
			return f.delete();
		}
		return true;
	}

	/**
	 * 将指定的字符串内容写入指定的文件.如果文件不存在,会先创建文件. 如果文件原有内容,会被覆盖.该方法不支持字节数组/字节流内容.
	 *
	 * @param fileName
	 *            要写入的文件 如为空,会立即返回.
	 * @param content
	 *            要写入的内容 如为空,会立即返回.
	 * @throws IOException
	 *             发生文件读取/创建失败时抛出.
	 * */
	public static final void writeToFile(String fileName, String content)
			throws IOException {
		writeToFile(fileName, content, DEFAULT_CHAR_SET);
	}

	/**
	 * 将指定的字符串内容写入指定的文件,制定字符集
	 *
	 * @param fileName
	 *            要写入的文件 如为空,会立即返回
	 * @param content
	 *            要写入的内容 如为空,会立即返回
	 * @param charSet
	 *            制定字符集
	 * @throws IOException
	 */
	public static final void writeToFile(String fileName, String content,
			String charSet) throws IOException {
		if (null == fileName || null == content)
			return;
		if (charSet == null || charSet.trim().length() == 0)
			charSet = DEFAULT_CHAR_SET;
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(createFile(fileName)), charSet);
		writer.write(content);
		writer.close();
	}

	public static final void writeToFile(File file, String content)
			throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(file), DEFAULT_CHAR_SET);
		writer.write(content);
		writer.close();
	}

	public static final void writeToFile(File file, String content,
			String charSet) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(file), charSet);
		writer.write(content);
		writer.close();
	}

	public static final void writeToFile(OutputStream out, String content)
			throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out,
				DEFAULT_CHAR_SET);
		writer.write(content);
		writer.close();
	}

	public static final void writeToFile(OutputStream out, String content,
			String charSet) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, charSet);
		writer.write(content);
		writer.close();
	}

	/**
	 * 将指定的字符串内容添加入指定的文件内容末尾.如果文件原来无内容,同writeToFile.
	 * 否则，自动添加在文件末尾.该方法不支持字节数组/字节流内容.
	 *
	 * @param fileName
	 *            要写入的文件 如为空,会立即返回.
	 * @param content
	 *            要写入的内容 如为空,会立即返回.
	 * @throws IOException
	 *             发生文件读取/创建失败时抛出.
	 * */
	public static void appendToFile(String fileName, String content)
			throws IOException {
		if (fileName == null || content == null)
			return;

		BufferedReader reader = new BufferedReader(new FileReader(
				createFile(fileName)));
		StringBuffer buff = new StringBuffer();
		String lineContent = null;
		while ((lineContent = reader.readLine()) != null) {
			buff = buff.append(lineContent).append("\n");
		}
		reader.close();

		FileWriter writer = new FileWriter(createFile(fileName));
		writer.write(buff.toString());
		writer.write(content);
		writer.close();
	}

	/**
	 * 根据文件名读出文本文件内容.
	 *
	 * @param fileName
	 *            包含路径的文件名.
	 * @throws IOException
	 *             发生文件读取错误时抛出.
	 */
	public static String readFromFile(String fileName) throws IOException {
		return readFromFile(fileName, DEFAULT_CHAR_SET);
	}

	/**
	 * 根据文件名读出文本文件内容
	 *
	 * @param fileName
	 *            包含路径的文件名
	 * @param charSet
	 *            设定的字符集
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(String fileName, String charSet)
			throws IOException {
		if (fileName == null || fileName.trim().length() == 0)
			return null;
		if (charSet == null || charSet.trim().length() == 0)
			charSet = DEFAULT_CHAR_SET;
		StringBuffer buffer = new StringBuffer();
		try (FileInputStream inputStream = new FileInputStream(fileName);
				InputStreamReader reader = new InputStreamReader(inputStream,
						charSet)) {

			char[] buf = new char[64];
			int count = 0;
			while ((count = reader.read(buf)) != -1) {
				buffer.append(buf, 0, count);
			}
		} catch (Exception e) {
			throw new IOException();
		}
		return buffer.toString();

	}

	public static String readFromFile(File file) throws IOException {
		return readFromFile(file, DEFAULT_CHAR_SET);
	}

	public static String readFromFile(File file, String charSet)
			throws IOException {
		if (charSet == null || charSet.trim().length() == 0)
			charSet = DEFAULT_CHAR_SET;
		StringBuffer buffer = new StringBuffer();
		try (FileInputStream inputStream = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(inputStream,
						charSet)) {
			char[] buf = new char[64];
			int count = 0;
			while ((count = reader.read(buf)) != -1) {
				buffer.append(buf, 0, count);
			}
		} catch (Exception e) {
			throw new IOException();
		}
		return buffer.toString();
	}

	public static String readFromFile(InputStream in) throws IOException {
		return readFromFile(in, DEFAULT_CHAR_SET);
	}

	public static String readFromFile(InputStream in, String charSet)
			throws IOException {
		if (charSet == null || charSet.trim().length() == 0)
			charSet = DEFAULT_CHAR_SET;
		InputStreamReader reader = new InputStreamReader(in, charSet);
		StringBuffer buffer = new StringBuffer();
		char[] buf = new char[64];
		int count = 0;
		while ((count = reader.read(buf)) != -1) {
			buffer.append(buf, 0, count);
		}
		return buffer.toString();
	}

	/**
	 * COPY文件.
	 *
	 * @param des
	 *            拷贝到的文件名（带路径）.
	 * @param src
	 *            要拷贝的文件名（带路径）.
	 * */
	public static void copyFile(String des, String src) throws IOException {
		FileInputStream fis = new FileInputStream(IoUtil.createFile(src));
		FileOutputStream fos = new FileOutputStream(IoUtil.createFile(des));
		byte[] b = new byte[1024 * 8];
		int len = 0;
		while ((len = fis.read(b)) > 0) {
			fos.write(b, 0, len);
		}
		fos.close();
		fis.close();
	}

	/**
	 * 删除此目录下的所有文件.
	 *
	 * @param filePath
	 *            要删除的文件夹.
	 * @return 是否删除成功.
	 * @exception IOException
	 *                出现IO操作失败时抛出.
	 * */
	public static boolean deleteFiles(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			return true;
		}

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null) {
				for (int i = 0, len = files.length; i < len; i++) {
					if (files[i].isFile()) {
						files[i].delete();
					} else {
						deleteFiles(files[i].getAbsolutePath());
					}
				}
			}
		}
		return f.delete();
	}

	/**
	 * 读取字节数
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transfer(in, out);
		return out.toByteArray();
	}

	/**
	 * 读取字节数
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(String filePath) throws IOException {
		InputStream in = null;
		try {
			FileLoader fileLoader = FileLoaderFactory.creatFileLoader(
					PathFileLoader.class, null);
			fileLoader.setFile(filePath);
			in = fileLoader.getInputStream();
			return readBytes(in);

		} finally {
			IoUtil.close(in);
		}

	}

	/**
	 * 关闭
	 *
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				if (log.isDebugEnabled())
					log.debug("failed to close stream", e);
			}
		}
	}

	/**
	 * 转换字节流
	 *
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public static long transfer(InputStream in, OutputStream out)
			throws IOException {
		long total = 0;
		byte[] buffer = new byte[BUFFERSIZE];
		for (int count; (count = in.read(buffer)) != -1;) {
			out.write(buffer, 0, count);
			total += count;
		}
		return total;
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 *
	 * @param inputFile
	 * @param ouputStream
	 * @throws IOException
	 */
	public static void zipFile(File inputFile, CZipOutputStream ouputStream)
			throws IOException {
		FileInputStream inputstream = null;
		try {
			if (inputFile.exists() && inputFile.isFile()) {

				inputstream = new FileInputStream(inputFile);
				CZipEntry entry = new CZipEntry(inputFile.getName());
				ouputStream.putNextEntry(entry);
				// 向压缩文件中输出数据
				transfer(inputstream, ouputStream);
				// ouputStream.closeEntry();
			} else {
				throw new BaseException("打包的文件" + inputFile.getName() + "不存在！");
			}

		} finally {
			if (inputstream != null)
				close(inputstream);
		}

	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 *
	 * @param inputFile
	 * @param zipOutputStream
	 * @throws IOException
	 */
	public static void zipFile(String fileName, InputStream inputStream,
			CZipOutputStream zipOutputStream) throws IOException {
		CZipEntry entry = new CZipEntry(fileName);
		zipOutputStream.putNextEntry(entry);
		// 向压缩文件中输出数据
		transfer(inputStream, zipOutputStream);
		zipOutputStream.closeEntry();

	}

	/**
	 * 获得文件名的扩展.如：/c/a.txt,返回.txt,如/c/a,则返回.unknown;
	 *
	 * @param fileName
	 *            文件名
	 * @return 以".xx"格式返回
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int index = fileName.lastIndexOf(".");
			if (index != -1 && index != fileName.length() - 1) {
				return fileName.substring(index);
			}
		}
		return ".unknown";
	}

	/**
	 * 创建文件
	 *
	 * @param file
	 * @param in
	 * @return 返回文件的字节数
	 */
	public static long createFile(File file, InputStream in) {

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		FileOutputStream out = null;

		try {
			out = new FileOutputStream(file);
			return transfer(in, out);
		} catch (Exception ex) {
			throw new BaseException("保存文件失败！");

		} finally {
			if (out != null) {
				IoUtil.close(out);
			}

			if (in != null) {
				IoUtil.close(in);
			}

		}
	}

	/**
	 * 去除文件路径，获取文件名称.如：/a/b.text,返回b.text.
	 *
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static String getFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	/**
	 * 去掉文件路径末尾分隔符
	 *
	 * @param filePath
	 * @return
	 */
	public static String trimEndFilePathSeparator(String filePath) {
		return trimEndFilePathSeparator(filePath, false);

	}

	/**
	 * 去掉文件路径末尾分隔符,以当前操作系统文件路径风格返回。
	 *
	 * <pre>
	 * /foo//               -->   /foo
	 * /foo/./              -->   /foo
	 * /foo/../bar          -->   /bar
	 * /foo/../bar/         -->   /bar
	 * /foo/../bar/../baz   -->   /baz
	 * //foo//./bar         -->   /foo/bar
	 * /../                 -->   null
	 * ../foo               -->   null
	 * foo/bar/..           -->   foo
	 * foo/../../bar        -->   null
	 * </pre>
	 *
	 * @param filePath
	 * @param unixSeparator
	 * @return
	 */
	public static String trimEndFilePathSeparator(String filePath,
			boolean unixSeparator) {
		filePath = FilenameUtils.normalizeNoEndSeparator(filePath);
		if (unixSeparator) {
			return toUnixFilePath(filePath);
		}
		return filePath;

	}

	/**
	 * 去除文件路径及扩展，获取文件基本名称.如：/a/b.text,返回b.
	 *
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static String getBaseFileName(String fileName) {
		return FilenameUtils.getBaseName(fileName);
	}

	/**
	 * 连接文件，以适应本地操作系统的文件路径返回
	 *
	 * @param rootPath
	 * @param fileName
	 * @return
	 */
	public static String concat(String rootPath, String fileName) {
		return concat(rootPath, fileName, false);
	}

	/**
	 *
	 *
	 * @param ch
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isSeparator(char ch) {
		return (ch == UNIX_SEPARATOR) || (ch == WINDOWS_SEPARATOR);
	}

	/**
	 * 连接文件，以unix separator 返回
	 *
	 * @param rootPath
	 * @param fileName
	 * @param unixSeparator
	 * @return
	 */
	public static String concat(String rootPath, String fileName,
			boolean unixSeparator) {
		fileName = FilenameUtils.normalize(rootPath + fileName);
		if (unixSeparator) {
			return toUnixFilePath(fileName);
		}
		return fileName;
	}

	/**
	 * 获取文件显示字节数
	 *
	 * @param size
	 * @return
	 */
	public static String countFileDisplaySize(long size) {
		String displaySize;
		if (size / ONE_GB > 0) {
			displaySize = format(Math.floor(size * 100.0 / ONE_GB) / 100) + "G";
		} else if (size / ONE_MB > 0) {
			displaySize = format(Math.floor(size * 100.0 / ONE_MB) / 100) + "M";
		} else if (size / ONE_KB > 0) {
			displaySize = format(Math.floor(size * 100.0 / ONE_KB) / 100) + "K";
		} else {
			displaySize = size + "Bytes";
		}
		return displaySize;
	}

	/**
	 * 将文件路径转换为unix的风格
	 *
	 * @param filePath
	 * @return
	 */
	public static String toUnixFilePath(String filePath) {
		return FilenameUtils.separatorsToUnix(filePath);
	}

	private static String format(double number) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);
		return format.format(number);
	}

}
