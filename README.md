# Metadata Scrubber

A Java command-line application to scrub metadata from images and videos, removing any evidence of origin.

## Features

- Removes EXIF and other metadata from image files (JPEG, PNG, GIF, BMP, TIFF, WebP)
- Strips metadata from video files (MP4, AVI, MKV, MOV, WMV, FLV) using FFmpeg
- Processes single files or directories recursively
- Command-line interface with options for input/output

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- FFmpeg installed (for video processing)
- ImageMagick installed (optional, for advanced image processing)
- Tesseract OCR installed (optional, for OCR capabilities with 13 languages support)

## Build

```bash
mvn clean package
```

This will create a shaded JAR in `target/metadata-scrubber-1.0-SNAPSHOT.jar`

## Usage

```bash
java -jar target/metadata-scrubber-1.0-SNAPSHOT.jar [options] <input>
```

### Options

- `<input>`: Input file or directory to process
- `-o, --output <dir>`: Output directory for scrubbed files (optional, overwrites originals if not specified)
- `-r, --recursive`: Process directories recursively
- `-h, --help`: Show help message
- `-V, --version`: Show version

### Examples

Scrub a single image:

```bash
java -jar target/metadata-scrubber-1.0-SNAPSHOT.jar image.jpg
```

Scrub all files in a directory and save to output folder:

```bash
java -jar target/metadata-scrubber-1.0-SNAPSHOT.jar -o scrubbed/ -r input_dir/
```

## Dependencies

- Apache Commons Imaging: For image metadata handling
- Picocli: For command-line interface
- SLF4J: For logging
- JCodec: For video processing (though FFmpeg is used for metadata stripping)

## Additional Tools

This project works best with the following additional tools installed:

### FFmpeg (Video Processing)

- **Purpose**: Handles video metadata stripping
- **Installation**: `winget install ffmpeg` or download from [https://ffmpeg.org/](https://ffmpeg.org/)
- **Usage**: Automatically called for video files

### ImageMagick (Advanced Image Processing)

- **Purpose**: Alternative image processing capabilities
- **Installation**: `winget install ImageMagick.ImageMagick`
- **Usage**: Can be used for additional image manipulation if needed

### Tesseract OCR (Text Recognition)

- **Purpose**: OCR capabilities for text extraction from images
- **Installation**: `winget install UB-Mannheim.TesseractOCR`
- **Languages Supported**: 13 languages (English, Spanish, German, Russian, Chinese, Japanese, Arabic, Persian, Polish, Korean, Urdu, Ukrainian, OSD)
- **Usage**: `tesseract image.png output.txt -l eng`

## Git

This project includes a comprehensive `.gitignore` file that excludes:

- Build artifacts (`target/`, `build/`, `out/`)
- IDE files (`.idea/`, `*.iml`, `.vscode/`)
- OS files (`.DS_Store`, `Thumbs.db`)
- Log files (`*.log`)
- Temporary files (`*.tmp`, `*.swp`)
- Local configuration files

