mkdir -p %{home}/.ssh;
rm -rf %{home}/.ssh/*;
su -l %{user} -c "ssh-keygen -P '' -t rsa -b 2048 -f %{home}/.ssh/id_rsa;"
touch %{home}/.ssh/authorized_keys;
chmod 700 %{home}/.ssh;
chmod 600 %{home}/.ssh/authorized_keys;
chown -R %{user}:%{user} %{home}/.ssh;