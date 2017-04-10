# Sufbo

## Goals

Sufbo aims to de-obfuscate the obfuscated programs by the name obfuscation tool, such as Dash-O.
Therefore, we collect opcode sequences of methods from existing Java packages in the Maven repository.

## Components

### Storage

This component extracts method information from given Java packages, and store them into a certain storage.

#### Stored information

* Group Id
    * Artifact Id
        * Version
            * Class name
                * Method name
                * signature
                * \# of opcode sequence
                * sha256 hash of opcode sequence
                * opcode sequence

### Search

This component searches method names based on its information from above storages.


## Usage

### スクリプト集

#### extract.sh

次の３種類からメソッドごとに上記の[Stored information](#stored_information)を取り出し，
csv形式（extracted.csv形式）で出力する．

* Maven repository
    * Maven repositoryの起点となるディレクトリを指定する．
    * ただし，そのディレクトリは，```repository```である必要がある．
* jarファイルの絶対パスのリスト（newest.list形式）
    * ```.list```で終わるファイル名である必要がある．
    * そのファイルの各行は，jarファイルの絶対パスである必要がある．
    * その絶対パスの途中に ```repository```という文字列が入っている必要がある．
        * そのディレクトリを起点として，groupId, artifactId, version を取り出している．
    * ただし，そのディレクトリは，```repository```である必要がある．
* 特定のjarファイル（絶対パス，相対パス）
    * 特定の jar ファイルを対象とする．ただし，別途オプションで artifactId, groupId, version を指定する必要がある．
        * ```extract.sh hoge.jar -a ARTIFACT_ID -g GROUP_ID -v VERSION```

#### newest.sh

入力として，Maven repositoryの起点ディレクトリパスを受け取り，最新バージョンを newest.list 形式で出力する．

#### freq.sh

extracted.csv 形式のファイルを受け取り，命令列の頻度を出力する（freq.csv形式）．
freq.csv形式は，```# of opcodes,freq```で各行に出力する．

#### heatmapscaler.sh

Heatmap の物差しを画像で出力する．
引数（入力項目）なし，何度実行しても，結果は同じ．

カレントディレクトリに ```scale.png```が出力される．

#### leaveoneout.sh

extracted.csv 形式のファイルを受け取り，leave one out で分析を行う．
同時に heatmap の画像ファイルもppm形式で出力する．
ただし，csvが大きなサイズの場合，画像ファイルも非常に膨大になる点に注意．

#### sort.sh

extracted.csv 形式のファイルを受け取り，opcode 数でソートして extracted.csv 形式で出力する．
ファイルサイズが大きい場合は，メモリ不足で落ちる．

#### split.sh

int型の値と，extracted.csv 形式のファイルを受け取る．
int型の値は分割数であり，その数だけ，extracted.csv 形式のファイルを出力する．
受け取ったファイルの名前を ```hoge.csv```とすると，出力するファイル名は，```hoge-?.csv```
となり，```?```部分は数値が入れられる．
どのファイルもほぼ同じ行数となる．
これらのファイルの使い道はまだない．

