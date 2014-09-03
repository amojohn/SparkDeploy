user=%{user}
pwd=%{pwd}
expect -c "
spawn passwd ${user}
expect {
    \"*New password\" {set timeout 300; send \"${pwd}\r\"; exp_continue}
    \"Retype new password:\" {send \"${pwd}\r\"; exp_continue;}
    eof
}"