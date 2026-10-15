param([Parameter(ValueFromRemainingArguments = $true)][string[]]$MavenArgs)
$ErrorActionPreference = 'Stop'
$version = '3.9.9'
$dir = Join-Path $PSScriptRoot "apache-maven-$version"
$exe = Join-Path $dir 'bin\mvn.cmd'
if (!(Test-Path $exe)) {
  $zip = Join-Path $PSScriptRoot 'maven.zip'
  $url = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$version/apache-maven-$version-bin.zip"
  [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
  Write-Host 'Baixando Maven na primeira execucao...'
  Invoke-WebRequest -Uri $url -OutFile $zip -UseBasicParsing
  $expected = ((Invoke-WebRequest -Uri "$url.sha512" -UseBasicParsing).Content.Trim() -split '\s+')[0]
  if ($expected -notmatch '^[a-fA-F0-9]{128}$') { throw 'Checksum de Maven invalido.' }
  if ((Get-FileHash $zip -Algorithm SHA512).Hash -ne $expected) { throw 'Checksum de Maven nao confere.' }
  Expand-Archive -Path $zip -DestinationPath $PSScriptRoot -Force
  Remove-Item $zip
}
& $exe @MavenArgs
exit $LASTEXITCODE
