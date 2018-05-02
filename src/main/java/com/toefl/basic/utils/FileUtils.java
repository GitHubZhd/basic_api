package com.toefl.basic.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Admin on 2015/6/24.
 */
public class FileUtils {
	public static Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 生成zip包，可以压缩单文件，单目录，文件和目录
     * @param fromPath 要压缩的路径，可以是文件也可以是目录
     * @param toZipFile 生成的zip文件的路径
     * @param includeRegex 包含哪些文件 ex:"*.java"
     * @param excludeRegex 非包含哪些文件 ex:"*.java"
     */
    public static void zipFile(String fromPath, String toZipFile, String includeRegex, String excludeRegex) {
        File srcdir = new File(fromPath);
        if (!srcdir.exists()) {
            throw new RuntimeException(fromPath + "不存在！");
        }

        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(new File(toZipFile));
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcdir);
        if (!StringUtils.isEmpty(includeRegex)) {
            fileSet.setIncludes(includeRegex); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
        }
        if (!StringUtils.isEmpty(excludeRegex)) {
            fileSet.setExcludes(excludeRegex); //排除哪些文件或文件夹
        }
        zip.addFileset(fileSet);
        zip.execute();
    }
	/**
	 * 创建文件夹
	 *
	 * @param strFilePath
	 *            文件夹路径
	 */
	public static boolean mkdirFolder(String strFilePath) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.mkdir();
			}
		} catch (Exception e) {
			log.error("新建目录操作出错" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	public static boolean createFile(String strFilePath, String strFileContent) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				bFlag = file.createNewFile();
			}
			if (bFlag == Boolean.TRUE) {
				// FileWriter fw = new FileWriter(file);
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				PrintWriter pw = new PrintWriter(out);
				pw.println(strFileContent.toString());
				pw.close();
			}
		} catch (Exception e) {
			log.error("新建文件操作出错" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	/**
	 * 删除文件
	 *
	 * @param strFilePath
	 * @return
	 */
	public static boolean removeFile(String strFilePath) {
		boolean result = false;
		if (strFilePath == null || "".equals(strFilePath)) {
			return result;
		}
		File file = new File(strFilePath);
		if (file.isFile() && file.exists()) {
			result = file.delete();
			if (result == Boolean.TRUE) {
				log.debug("[REMOE_FILE:" + strFilePath + "删除成功!]");
			} else {
				log.debug("[REMOE_FILE:" + strFilePath + "删除失败]");
			}
		}
		return result;
	}

	/**
	 * 删除文件夹(包括文件夹中的文件内容，文件夹)
	 *
	 * @param strFolderPath
	 * @return
	 */
	public static boolean removeFolder(String strFolderPath) {
		boolean bFlag = false;
		try {
			if (strFolderPath == null || "".equals(strFolderPath)) {
				return bFlag;
			}
			File file = new File(strFolderPath.toString());
			bFlag = file.delete();
			if (bFlag == Boolean.TRUE) {
				log.debug("[REMOE_FOLDER:" + file.getPath() + "删除成功!]");
			} else {
				log.debug("[REMOE_FOLDER:" + file.getPath() + "删除失败]");
			}
		} catch (Exception e) {
			log.error("FLOADER_PATH:" + strFolderPath + "删除文件夹失败!");
			e.printStackTrace();
		}
		return bFlag;
	}

	/**
	 * 移除所有文件
	 *
	 * @param strPath
	 */
	public static void removeAllFile(String strPath) {
		File file = new File(strPath);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] fileList = file.list();
		File tempFile = null;
		for (int i = 0; i < fileList.length; i++) {
			if (strPath.endsWith(File.separator)) {
				tempFile = new File(strPath + fileList[i]);
			} else {
				tempFile = new File(strPath + File.separator + fileList[i]);
			}
			if (tempFile.isFile()) {
				tempFile.delete();
			}
			if (tempFile.isDirectory()) {
				removeAllFile(strPath + "/" + fileList[i]);// 下删除文件夹里面的文件
				removeFolder(strPath + "/" + fileList[i]);// 删除文件夹
			}
		}
	}

	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null; // 读入原文件
		FileOutputStream fs = null;
		File newFile = new File(newPath);
		try {
			int bytesum = 0;
			int byteread = 0;

			if (!newFile.exists()) {
				newFile.getParentFile().mkdirs();
			}
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				log.debug("[COPY_FILE:" + oldfile.getPath() + "复制文件成功!]");
			}
			removeFile(oldPath);
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			e.printStackTrace();
			newFile.delete();
		}finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/ " + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					log.debug("[COPY_FILE:" + temp.getPath() + "复制文件成功!]");
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/ " + file[i], newPath + "/ " + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错 ");
			e.printStackTrace();
		}
	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
//	 	removeFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		// removeFolder(oldPath);
	}

	public static void zipFile(String rootPath, List<String> path, File zipFile) throws IOException {
		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		FileInputStream in = null;
		List<String> tempList = new ArrayList<>();
		try {
			if (!zipFile.exists()) {
				zipFile.getParentFile().mkdirs();
				zipFile.createNewFile();
			}

			out = new ZipOutputStream(new FileOutputStream(zipFile));
			// ZipOutputStream类：完成文件或文件夹的压缩
			for (int i = 0; i < path.size(); i++) {
				if (tempList.contains(path.get(i))) {
					continue;
				} else {
					tempList.add(path.get(i));
					File suorce = new File(rootPath + path.get(i));
					in = new FileInputStream(suorce);
					out.putNextEntry(new ZipEntry(suorce.getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
				}
			}
		} catch (Exception e) {
			in.close();
			out.closeEntry();
			out.close();
			FileUtils.removeFile(zipFile.getPath());
			throw e;
		} finally {
			in.close();
			out.closeEntry();
			out.close();
		}

	}

	public static Boolean unzip(String fileName, String unzipFilePath) {
		Boolean result = false;
		int buffer = 2048;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(fileName);
			Enumeration emu = zipFile.entries();
			int i = 0;
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(unzipFilePath + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				File file = new File(unzipFilePath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, buffer);
				int count;
				byte data[] = new byte[buffer];
				while ((count = bis.read(data, 0, buffer)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			try {
				zipFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

	public static String readFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) { // 文件存在时
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuilder buffer = new StringBuilder();
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					buffer.append(lineTxt);
				}
				read.close();
				log.debug("[readFile:" + filePath + "读取文件成功!]");
				return buffer.toString();
			} catch (IOException e) {
				log.debug("[readFile:" + filePath + "读取文件失败!]");
				e.printStackTrace();
				return "";
			}
		}
		return "";
	}

	public static void readByApacheZipFile(String archive, String decompressDir){
		BufferedInputStream bi;
		try {
			org.apache.tools.zip.ZipFile zf = new org.apache.tools.zip.ZipFile(archive, "utf-8");//支持中文
			Enumeration e = zf.getEntries();
			while (e.hasMoreElements()) {
				org.apache.tools.zip.ZipEntry ze2 = (org.apache.tools.zip.ZipEntry) e.nextElement();
				String entryName = ze2.getName();
				String path = decompressDir + "/" + entryName;
				if (ze2.isDirectory()) {
					//System.out.println("正在创建解压目录 - " + entryName);
					File decompressDirFile = new File(path);
					if (!decompressDirFile.exists()) {
						decompressDirFile.mkdirs();
					}
				} else {
					//System.out.println("正在创建解压文件 - " + entryName);
					String fileDir = path.substring(0, path.lastIndexOf("/"));
					File fileDirFile = new File(fileDir);
					if (!fileDirFile.exists()) {
						fileDirFile.mkdirs();
					}
					BufferedOutputStream bos = null;

						bos = new BufferedOutputStream(new FileOutputStream(
								decompressDir + "/" + entryName));
						bi = new BufferedInputStream(zf.getInputStream(ze2));
						byte[] readContent = new byte[1024];
						int readCount = bi.read(readContent);
						while (readCount != -1) {
							bos.write(readContent, 0, readCount);
							readCount = bi.read(readContent);
						}
						bos.close();



				}
			}
			zf.close();
		} catch (FileNotFoundException e1) {
			System.out.println("解压文件" + archive + "失败");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("解压文件"+archive+"失败");
			e1.printStackTrace();
		}
	}
	//保存上传文件
	public static File saveFile(MultipartFile file, String path, String ext) throws IOException {
		String fileName = UUIDUtils.create()+ext;
		String filePath = EnvPropertiesUtil.getProperties(path);
		File targetFile = new File(filePath, fileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		file.transferTo(targetFile);
		return targetFile;
	}


	public static void writeFile(String filePath, String str) {
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); //如果追加方式用true
			StringBuffer sb = new StringBuffer();
			sb.append(str);
			out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}
