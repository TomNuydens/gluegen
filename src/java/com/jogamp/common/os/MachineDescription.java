/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Copyright (c) 2010 JogAmp Community. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 * 
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

package com.jogamp.common.os;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.jogamp.common.nio.Buffers;

/**
 * For alignment and size see {@link com.jogamp.gluegen}
 */
public class MachineDescription {
  /*                      arch   os          int, long, float, doubl, ldoubl,  ptr,   page */
  final static int[] size_armeabi         =  { 4,    4,     4,     8,      8,    4,   4096 };
  final static int[] size_x86_32_unix     =  { 4,    4,     4,     8,     12,    4,   4096 };
  final static int[] size_x86_32_windows  =  { 4,    4,     4,     8,     12,    4,   4096 };
  final static int[] size_x86_64_unix     =  { 4,    8,     4,     8,     16,    8,   4096 };
  final static int[] size_x86_64_windows  =  { 4,    4,     4,     8,     16,    8,   4096 };

  /*                       arch   os          i8, i16, i32, i64, int, long, float, doubl, ldoubl, ptr */                            
  final static int[] align_armeabi        =  { 1,   2,   4,   8,   4,    4,     4,     8,      8,   4 };
  final static int[] align_x86_32_unix    =  { 1,   2,   4,   4,   4,    4,     4,     4,      4,   4 };
  final static int[] align_x86_32_windows =  { 1,   2,   4,   8,   4,    4,     4,     8,      4,   4 };
  final static int[] align_x86_64_unix    =  { 1,   2,   4,   8,   4,    8,     4,     8,     16,   8 };
  final static int[] align_x86_64_windows =  { 1,   2,   4,   8,   4,    4,     4,     8,     16,   8 };
  
  public enum Config {
      ARM_EABI(size_armeabi, align_armeabi),
      X86_32_UNIX(size_x86_32_unix, align_x86_32_unix),
      X86_64_UNIX(size_x86_64_unix, align_x86_64_unix),
      X86_32_WINDOWS(size_x86_32_windows, align_x86_32_windows),
      X86_64_WINDOWS(size_x86_64_windows, align_x86_64_windows);
      
      public final int intSizeInBytes;
      public final int longSizeInBytes;
      public final int floatSizeInBytes;
      public final int doubleSizeInBytes;
      public final int ldoubleSizeInBytes;
      public final int pointerSizeInBytes;
      public final int pageSizeInBytes;
      
      public final int int8AlignmentInBytes;
      public final int int16AlignmentInBytes;
      public final int int32AlignmentInBytes;
      public final int int64AlignmentInBytes;
      public final int intAlignmentInBytes;
      public final int longAlignmentInBytes;
      public final int floatAlignmentInBytes;
      public final int doubleAlignmentInBytes;
      public final int ldoubleAlignmentInBytes;
      public final int pointerAlignmentInBytes;
      
      Config(int[] sizes, int[] alignments) {
          int i=0;
          intSizeInBytes = sizes[i++];
          longSizeInBytes = sizes[i++];;
          floatSizeInBytes = sizes[i++];;
          doubleSizeInBytes = sizes[i++];;
          ldoubleSizeInBytes = sizes[i++];;
          pointerSizeInBytes = sizes[i++];;
          pageSizeInBytes = sizes[i++];;
          
          i=0;
          int8AlignmentInBytes = alignments[i++];
          int16AlignmentInBytes = alignments[i++];
          int32AlignmentInBytes = alignments[i++];
          int64AlignmentInBytes = alignments[i++];
          intAlignmentInBytes = alignments[i++];
          longAlignmentInBytes = alignments[i++];
          floatAlignmentInBytes = alignments[i++];
          doubleAlignmentInBytes = alignments[i++];
          ldoubleAlignmentInBytes = alignments[i++];
          pointerAlignmentInBytes = alignments[i++];          
      }
  }

  
  final private boolean runtimeValidated;
  
  final private boolean littleEndian;
    
  final private int int8SizeInBytes = 1;
  final private int int16SizeInBytes = 2;
  final private int int32SizeInBytes = 4;
  final private int int64SizeInBytes = 8;
  
  final private int intSizeInBytes;
  final private int longSizeInBytes;
  final private int floatSizeInBytes;
  final private int doubleSizeInBytes;
  final private int ldoubleSizeInBytes;
  final private int pointerSizeInBytes;
  final private int pageSizeInBytes;
  final private boolean is32Bit;
    
  final private int int8AlignmentInBytes;
  final private int int16AlignmentInBytes;
  final private int int32AlignmentInBytes;
  final private int int64AlignmentInBytes;
  final private int intAlignmentInBytes;
  final private int longAlignmentInBytes;
  final private int floatAlignmentInBytes;
  final private int doubleAlignmentInBytes;
  final private int ldoubleAlignmentInBytes;
  final private int pointerAlignmentInBytes;

  public static boolean queryIsLittleEndian() {
    ByteBuffer tst_b = Buffers.newDirectByteBuffer(Buffers.SIZEOF_INT); // 32bit in native order
    IntBuffer tst_i = tst_b.asIntBuffer();
    ShortBuffer tst_s = tst_b.asShortBuffer();
    tst_i.put(0, 0x0A0B0C0D);
    return 0x0C0D == tst_s.get(0);
  }
  
  public static MachineDescription createStaticArmEABI() {
      return new MachineDescription(false, queryIsLittleEndian(), Config.ARM_EABI);
  }
  public static MachineDescription createStaticUnix32() {
      return new MachineDescription(false, queryIsLittleEndian(), Config.X86_32_UNIX);
  }
  public static MachineDescription createStaticUnix64() {
      return new MachineDescription(false, queryIsLittleEndian(), Config.X86_64_UNIX);
  }
  public static MachineDescription createStaticWindows32() {
      return new MachineDescription(false, queryIsLittleEndian(), Config.X86_32_WINDOWS);
  }
  public static MachineDescription createStaticWindows64() {
      return new MachineDescription(false, queryIsLittleEndian(), Config.X86_64_WINDOWS);
  }
  public static MachineDescription createStatic(boolean is32BitByCPUArch) {
      if(is32BitByCPUArch) {
        if(Platform.getCPUFamily() == Platform.CPUFamily.ARM) {
            return createStaticArmEABI();
        } else if(Platform.getOSType() == Platform.OSType.WINDOWS) {
            return createStaticWindows32();            
        }
        return createStaticUnix32();            
      } else {
        if(Platform.getOSType() == Platform.OSType.WINDOWS) {
            return createStaticWindows64();                        
        }
        return createStaticUnix64();            
      }      
  }
  
  public MachineDescription(boolean runtimeValidated,
                            boolean littleEndian,
                            Config config) {
    this.runtimeValidated = runtimeValidated;    
    this.littleEndian = littleEndian;
    
    this.intSizeInBytes     = config.intSizeInBytes;
    this.longSizeInBytes    = config.longSizeInBytes;
    this.floatSizeInBytes   = config.floatSizeInBytes;
    this.doubleSizeInBytes  = config.doubleSizeInBytes;
    this.ldoubleSizeInBytes = config.ldoubleSizeInBytes;
    this.pointerSizeInBytes = config.pointerSizeInBytes;
    this.pageSizeInBytes    = config.pageSizeInBytes; 
    this.is32Bit            = 4 == config.pointerSizeInBytes;

    this.int8AlignmentInBytes    = config.int8AlignmentInBytes;
    this.int16AlignmentInBytes   = config.int16AlignmentInBytes;
    this.int32AlignmentInBytes   = config.int32AlignmentInBytes;
    this.int64AlignmentInBytes   = config.int64AlignmentInBytes;
    this.intAlignmentInBytes     = config.intAlignmentInBytes;
    this.longAlignmentInBytes    = config.longAlignmentInBytes;
    this.floatAlignmentInBytes   = config.floatAlignmentInBytes;
    this.doubleAlignmentInBytes  = config.doubleAlignmentInBytes;
    this.ldoubleAlignmentInBytes = config.ldoubleAlignmentInBytes;
    this.pointerAlignmentInBytes = config.pointerAlignmentInBytes;      
  }
  
  public MachineDescription(boolean runtimeValidated,
                            boolean littleEndian,
          
                            int intSizeInBytes,
                            int longSizeInBytes,
                            int floatSizeInBytes,
                            int doubleSizeInBytes,
                            int ldoubleSizeInBytes,
                            int pointerSizeInBytes,
                            int pageSizeInBytes,
                            
                            int int8AlignmentInBytes,
                            int int16AlignmentInBytes,
                            int int32AlignmentInBytes,
                            int int64AlignmentInBytes,
                            int intAlignmentInBytes,
                            int longAlignmentInBytes,
                            int floatAlignmentInBytes,
                            int doubleAlignmentInBytes,
                            int ldoubleAlignmentInBytes,
                            int pointerAlignmentInBytes) {
    this.runtimeValidated = runtimeValidated;    
    this.littleEndian = littleEndian;
    
    this.intSizeInBytes     = intSizeInBytes;
    this.longSizeInBytes    = longSizeInBytes;
    this.floatSizeInBytes   = floatSizeInBytes;
    this.doubleSizeInBytes  = doubleSizeInBytes;
    this.ldoubleSizeInBytes = ldoubleSizeInBytes;
    this.pointerSizeInBytes = pointerSizeInBytes;
    this.pageSizeInBytes    = pageSizeInBytes; 
    this.is32Bit            = 4 == pointerSizeInBytes;

    this.int8AlignmentInBytes    = int8AlignmentInBytes;
    this.int16AlignmentInBytes   = int16AlignmentInBytes;
    this.int32AlignmentInBytes   = int32AlignmentInBytes;
    this.int64AlignmentInBytes   = int64AlignmentInBytes;
    this.intAlignmentInBytes     = intAlignmentInBytes;
    this.longAlignmentInBytes    = longAlignmentInBytes;
    this.floatAlignmentInBytes   = floatAlignmentInBytes;
    this.doubleAlignmentInBytes  = doubleAlignmentInBytes;
    this.ldoubleAlignmentInBytes = ldoubleAlignmentInBytes;
    this.pointerAlignmentInBytes = pointerAlignmentInBytes;
  }
  
  /**
   * @return true if all values are validated at runtime, otherwise false (i.e. for static compilation w/ preset values)
   */
  public final boolean isRuntimeValidated() {
      return runtimeValidated;
  }
  
  /**
   * Returns true only if this system uses little endian byte ordering.
   */
  public final boolean isLittleEndian() {
      return littleEndian;
  }

  /**
   * Returns true if this JVM/ARCH is 32bit.
   */
  public final boolean is32Bit() {
    return is32Bit;
  }

  /**
   * Returns true if this JVM/ARCH is 64bit.
   */
  public final  boolean is64Bit() {
    return !is32Bit;
  }
  
  public final int intSizeInBytes()     { return intSizeInBytes;    }
  public final int longSizeInBytes()    { return longSizeInBytes;   }
  public final int int8SizeInBytes()    { return int8SizeInBytes;  }
  public final int int16SizeInBytes()   { return int16SizeInBytes;  }
  public final int int32SizeInBytes()   { return int32SizeInBytes;  }
  public final int int64SizeInBytes()   { return int64SizeInBytes;  }
  public final int floatSizeInBytes()   { return floatSizeInBytes;  }
  public final int doubleSizeInBytes()  { return doubleSizeInBytes; }
  public final int ldoubleSizeInBytes() { return ldoubleSizeInBytes; }
  public final int pointerSizeInBytes() { return pointerSizeInBytes; }
  public final int pageSizeInBytes()    { return pageSizeInBytes; }
  
  public final int intAlignmentInBytes()     { return intAlignmentInBytes;    }
  public final int longAlignmentInBytes()    { return longAlignmentInBytes;   }
  public final int int8AlignmentInBytes()    { return int8AlignmentInBytes;  }
  public final int int16AlignmentInBytes()   { return int16AlignmentInBytes;  }
  public final int int32AlignmentInBytes()   { return int32AlignmentInBytes;  }
  public final int int64AlignmentInBytes()   { return int64AlignmentInBytes;  }
  public final int floatAlignmentInBytes()   { return floatAlignmentInBytes;  }
  public final int doubleAlignmentInBytes()  { return doubleAlignmentInBytes; }
  public final int ldoubleAlignmentInBytes() { return ldoubleAlignmentInBytes; }
  public final int pointerAlignmentInBytes() { return pointerAlignmentInBytes; }
  
  /**
   * @return number of pages required for size in bytes
   */
  public int pageCount(int size) {
    return ( size + ( pageSizeInBytes - 1) ) / pageSizeInBytes ; // integer arithmetic
  }
    
  /**
   * @return page aligned size in bytes
   */
  public int pageAlignedSize(int size) {
    return pageCount(size) * pageSizeInBytes;
  }    
  
    public StringBuilder toString(StringBuilder sb) {
        if(null==sb) {
            sb = new StringBuilder();
        }
        sb.append("MachineDescription: runtimeValidated ").append(isRuntimeValidated()).append(", littleEndian ").append(isLittleEndian()).append(", 32Bit ").append(is32Bit()).append(", primitive size / alignment:").append(Platform.getNewline());
        sb.append("  int8    ").append(int8SizeInBytes)   .append(" / ").append(int8AlignmentInBytes);
        sb.append(", int16   ").append(int16SizeInBytes)  .append(" / ").append(int16AlignmentInBytes).append(Platform.getNewline());
        sb.append("  int     ").append(intSizeInBytes)    .append(" / ").append(intAlignmentInBytes);
        sb.append(", long    ").append(longSizeInBytes)   .append(" / ").append(longAlignmentInBytes).append(Platform.getNewline());
        sb.append("  int32   ").append(int32SizeInBytes)  .append(" / ").append(int32AlignmentInBytes);
        sb.append(", int64   ").append(int64SizeInBytes)  .append(" / ").append(int64AlignmentInBytes).append(Platform.getNewline());
        sb.append("  float   ").append(floatSizeInBytes)  .append(" / ").append(floatAlignmentInBytes);
        sb.append(", double  ").append(doubleSizeInBytes) .append(" / ").append(doubleAlignmentInBytes);
        sb.append(", ldouble ").append(ldoubleSizeInBytes).append(" / ").append(ldoubleAlignmentInBytes).append(Platform.getNewline());
        sb.append("  pointer ").append(pointerSizeInBytes).append(" / ").append(pointerAlignmentInBytes);
        sb.append(", page    ").append(pageSizeInBytes);                
        return sb;
    }
    
    @Override
    public String toString() {
        return toString(null).toString();
    }
  
}
