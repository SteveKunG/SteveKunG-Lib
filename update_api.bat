xcopy /s /Y "M:\Modding\SteveKunG-Lib\1.12.2\src" "M:\Modding\MorePlanets\1.13-pre\dependencies"
xcopy /s /Y "M:\Modding\SteveKunG-Lib\1.12.2\src" "M:\Modding\Indicatia\1.13-pre\dependencies"

rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\classes"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\dependency-cache"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\libs"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\resources"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\sources"
set GRADLE_USER_HOME=M:\Modding\SteveKunG-Lib\1.12.2\.gradle_data
gradlew deobfJar