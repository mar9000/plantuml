/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2014, Arnaud Roques
 *
 * Project Info:  http://plantuml.sourceforge.net
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.code;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionZlib implements Compression {

	public byte[] compress(byte[] in) {
		int len = in.length * 2;
		byte[] result = null;
		while (result == null) {
			result = tryCompress(in, len);
			len *= 2;
		}
		return result;
	}

	private byte[] tryCompress(byte[] in, final int len) {
		// Compress the bytes
		final Deflater compresser = new Deflater(9, true);
		compresser.setInput(in);
		compresser.finish();

		final byte[] output = new byte[len];
		final int compressedDataLength = compresser.deflate(output);
		if (compresser.finished() == false) {
			return null;
		}
		final byte[] result = copyArray(output, compressedDataLength);
		return result;
	}

	public byte[] decompress(byte[] in) throws IOException {

		final byte in2[] = new byte[in.length + 256];
		for (int i = 0; i < in.length; i++) {
			in2[i] = in[i];
		}

		int len = in.length * 5;
		byte[] result = null;
		while (result == null) {
			result = tryDecompress(in2, len);
			len *= 2;
		}
		return result;
	}

	private byte[] tryDecompress(byte[] in, final int len) throws IOException {
		// Decompress the bytes
		final byte[] tmp = new byte[len];
		final Inflater decompresser = new Inflater(true);
		decompresser.setInput(in);
		try {
			final int resultLength = decompresser.inflate(tmp);
			if (decompresser.finished() == false) {
				return null;
			}
			decompresser.end();

			final byte[] result = copyArray(tmp, resultLength);
			return result;
		} catch (DataFormatException e) {
			// e.printStackTrace();
			throw new IOException(e.toString());
		}
	}

	private byte[] copyArray(final byte[] data, final int len) {
		final byte[] result = new byte[len];
		for (int i = 0; i < result.length; i++) {
			result[i] = data[i];
		}
		return result;
	}

}
