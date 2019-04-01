@echo off

rem -------------------------------------------------------------------------
rem
rem 使用说明：
rem
rem 1: 该脚本用于别的项目时只需要修改 MAIN_CLASS 即可运行
rem
rem 2: JAVA_OPTS 可传入标准的 java 命令行参数,例如 -Xms256m -Xmx1024m 这类常用参数
rem
rem -------------------------------------------------------------------------

setlocal & pushd


rem 启动入口类,该脚本文件用于别的项目时要改这里
set MAIN_CLASS=com.fuchuan.customerservice.App

rem Java 命令行参数,根据需要开启下面的配置,改成自己需要的,注意等号前后不能有空格
rem set "JAVA_OPTS=-Xms256m -Xmx1024m"

set APP_BASE_PATH=%~dp0
set CP=%APP_BASE_PATH%config;%APP_BASE_PATH%lib\*
java -Xverify:none %JAVA_OPTS% -cp %CP% %MAIN_CLASS%


endlocal & popd
pause



