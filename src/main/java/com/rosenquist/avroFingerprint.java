package com.rosenquist;

import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Minimalist utility to emit the fingerprint of an Avro schema.
 */
public class avroFingerprint {
    public static void main(final String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length >= 1) {
            final Schema schema = new Schema.Parser().parse(new File(args[0]));

            final long fp = SchemaNormalization.parsingFingerprint64(schema);
            System.out.println(Long.toUnsignedString(fp));
            System.out.println(Long.toUnsignedString(fp, 16));

            final byte[] bytes = SchemaNormalization.parsingFingerprint("CRC-64-AVRO", schema);
            System.out.print("\"");
            for (byte b : bytes) {
                System.out.format("\\x%02X", b);
            }
            System.out.println("\"");
        } else {
            System.err.println("Usage: avro-fingerprint <avro-JSON-schema-file>");
        }
    }
}
