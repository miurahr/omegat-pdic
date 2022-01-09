# PDic dictionary lookup plugin for OmegaT

PDIC is a proprietary dictionary format of PDIC/Unicode application.
PDIC/Unicode is a popular dictinary authoring and lookup application on Windows in Japan.
It is a shareware but its author published its dictionary format for developers to help sharing dictionary data. 

The OmegaT driver only support latest version of PDIC format specification, Version 6.00 and 6.10. It also provide a support for Version 5.0 experimentally.

When you already have a PDIC dictinary data, you are recommend to export your data in latest PDIC/Unicode format to use it with OmegaT.

## PDIC/Unicode

PDIC/Unicode is a dictionary lookup shareware that is popular in Japan.
It has been actively maintained and released. It supports only on MS Windows.

http://pdic.la.coocan.jp/unicode/  (Japanese)

There are a few dictionary data (CD-ROM/DVD-ROM)  published on retail book stores
and EIJIRO is most popular one, that is formatted with PDIC binary format (with encryption).
which bundles a modified version of PDIC/Unicode shareware.

WARNING: The omegat plugin does NOT support EIJIRO above


##  PDIC dictionary format

There are several formats are known and releases its versions.

* PDIC binary formats

   * Unicode formats:

   Unicode is supported in the format specification Version 5.00
   that is introduced with PDIC/Unicode Ver 5.xx.
   Latest uses [the format specification Version 6.10](http://pdic.la.coocan.jp/unicode/dic-spec.html)
   The plugin support version 6.00 and 6.10.

   * Older formats:

   PDIC has old versions named PDIC for Win32 which uses the format specification version 4.00

* PDIC text formats

   PDIC for Win32 and PDIC/Unicode has exportd text formats for migration/exchange.

    * PDIC line-by-line text format

    * PDIC CSV text format


## How to use dictionaries published

There are several published dictionaries on freeware/shareware distribution site.
Most of these are published in older days, which format is version 4.00 or Text format.

If you want to use these with OmegaT and the plugin, you need to convert to
latest format version using PDIC/Unicode shareware.

1. load the dictionary with PDIC/Unicode on Windows
2. export the dictionary with latest version
3. copy exported dictionary (*.DIC) file into OmegaT project dictionary folder

## Test data

- [Chinese-Japanese unicode dictionary published in 2012.](http://www.vector.co.jp/soft/data/writing/se304431.html)

- [Český  language dictionary](https://czechdicjp.jimdofree.com/%E8%BE%9E%E6%9B%B8%E3%81%AE%E8%B3%BC%E5%85%A5/%EF%BC%91-wiindows%E7%89%88/)

- Not working: [French-Japanese dictionary](http://www.vector.co.jp/soft/dl/win95/edu/se217092.html)

French dictionary uses an old format version 4.00, that is out of the plugin support.
It can be used when export and convert to latest format version.

## Dependency

- The plugin is tested with OmegaT 5.6.
- The plugin depends on PDIC4j library.

## License

This project is distributed under the GNU general public license version 3 or later.
