rd /s /q "M:\Modding\Indicatia\1.12.2\dependencies\main\java\stevekung\mods\stevekunglib"
rd /s /q "M:\Modding\MorePlanets\1.12.2\dependencies\main\java\stevekung\mods\stevekunglib"

xcopy /s /Y "M:\Modding\SteveKunG-Lib\1.12.2\src" "M:\Modding\Indicatia\1.12.2\dependencies"
xcopy /s /Y "M:\Modding\SteveKunG-Lib\1.12.2\src" "M:\Modding\MorePlanets\1.12.2\dependencies"

rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\classes"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\dependency-cache"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\libs"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\resources"
rd /s /q "M:\Modding\SteveKunG-Lib\1.12.2\build\sources"
set GRADLE_USER_HOME=C:\Users\SteveKunG\.gradle_1.12.2
gradlew deobfJar