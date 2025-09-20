# Metadata Scrubber

A Java command-line application to scrub metadata from images and videos, removing any evidence of origin.

## Features

- Removes EXIF and other metadata from image files (JPEG, PNG, GIF, BMP, TIFF, WebP)
- Strips metadata from video files (MP4, AVI, MKV, MOV, WMV, FLV) using FFmpeg
- Processes single files or directories recursively
- Command-line interface with options for input/output

## Prerequisites

- Java 11 or higher
- FFmpeg installed (for video processing)

## Installation

### Option 1: Download Pre-built Release (Recommended)

1. Go to the [Releases](https://github.com/ergonomech/metadata_scrubbers/releases) page
2. Download the latest `metadata-scrubber-X.X.X.jar` file
3. Place it in your desired directory

### Option 2: Build from Source

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

## FFmpeg Installation

### Windows
```bash
winget install ffmpeg
```

### macOS
```bash
brew install ffmpeg
```

### Ubuntu/Debian
```bash
sudo apt update
sudo apt install ffmpeg
```

### Verify Installation
```bash
ffmpeg -version
```

## Development

This project uses Maven for build management and includes a comprehensive `.gitignore` for Java development.

### Dependencies

- Picocli: Command-line interface framework
- ImageIO (built-in): Image metadata handling

## License

See LICENSE file.

