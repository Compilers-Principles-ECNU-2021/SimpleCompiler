READ ME
===

# 1. 配置环境

1. 安装根目录下的安装包 stable_windows_10_cmake_Release_x64_graphviz-install-2.47.2-win64.exe。
2. 将根目录下的配置文件 config.properties 中的路径替换为本地的 Graphviz 安装路径。
3. 将GraphViz里面的TEMP_DIR改为自己电脑存在的任意一个文件夹，存储临时文件。

#2. 使用该编译器

1. 打开UI类，启动main函数。
2. 可以文件菜单栏下的打开选择要测试的文件，或者在代码编辑区写入要运行的代码。
3. 点击运行按钮，运行代码。
4. 运行结束后，点击清空按钮，重新选择新的需要测试的代码。

#3. 文件介绍

1. DotGraph.png是执行编译器生成的语法树，每执行一次，就会重新更新图片的内容。
2. result.txt是程序编译器运行的结果。
3. tacCode.txt是程序编译器运行过程中产生的三地址。
4. test.txt是每次程序编译器运行的测试用例，当打开文件时或者从UI输入的输入时，内容都会写到这个文件。
5. testError.txt是程序编译器运行过程中产生的错误信息的存储位置。
6. testOut.txt是程序编译器运行过程中终端的输出内容。
7. usedGrammar.txt是程序编译器运行过程中使用过的语法。



