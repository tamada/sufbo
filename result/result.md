# 実験結果（途中）

Maven リポジトリからメソッドごとに命令列を取り出して，散布図にした．
横軸がopcodeの数，縦軸がその頻度を表している．

![散布図](opcode-frequencies.png)

\# of opcodesが小さすぎるもの，frequencies が低すぎるものは十分な情報がないとして推薦の対象から削除する．
\# of opcodes > 10, freq > 100 くらいでまずやってみる？

