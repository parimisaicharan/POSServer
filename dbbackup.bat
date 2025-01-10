osql -Utsaf -Pfast -S%COMPUTERNAME% -q EXIT("DBCC shrinkdatabase(TSAF)")
osql -Utsaf -Pfast -S%COMPUTERNAME% -q EXIT("BACKUP DATABASE TSAF to disk='d:\POSdbbackup\tsafserver.data' WITH INIT")
exit
