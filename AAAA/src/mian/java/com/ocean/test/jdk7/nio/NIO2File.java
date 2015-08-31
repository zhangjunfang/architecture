package com.ocean.test.jdk7.nio;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

//NIO 2 file APi filesystem filestore
public class NIO2File {

	public static void main(String[] args) {
		try {
			// FileSystems.getDefault().getPath(first, more)
			Path path = Paths.get(System.getProperty("user.home"), "www",
					"pyweb.settings");
			Path real_path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
			System.out.println("Path to real path: " + real_path);

			System.out.println("Number of name elements in path: "
					+ path.getNameCount());
			for (int i = 0; i < path.getNameCount(); i++) {
				System.out.println("Name element " + i + " is: "
						+ path.getName(i));
			}
			System.out.println("Subpath (0,3): " + path.subpath(0, 3));

			File path_to_file = path.toFile();
			Path file_to_path = path_to_file.toPath();
			System.out.println("Path to file name: " + path_to_file.getName());
			System.out.println("File to path: " + file_to_path.toString());

			Path base = Paths.get(System.getProperty("user.home"), "www",
					"pyweb.settings");
			// resolve AEGON.txt file
			Path path1 = base.resolve("django.wsgi");
			System.out.println(path1.toString());

			Path path2 = base.resolveSibling(".bashrc");
			System.out.println(path2.toString());

			Path path01_to_path02 = path1.relativize(path2);
			System.out.println(path01_to_path02);

			try {
				boolean check = Files.isSameFile(path1.getParent(),
						path2.getParent());
				if (check) {
					System.out.println("The paths locate the same file!"); // true
				} else {
					System.out
							.println("The paths does not locate the same file!");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			boolean sw = path1.startsWith("/rafaelnadal/tournaments");
			boolean ew = path1.endsWith("django.wsgi");
			System.out.println(sw);
			System.out.println(ew);

			for (Path name : path1) {
				System.out.println(name);
			}

		} catch (NoSuchFileException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}
}
