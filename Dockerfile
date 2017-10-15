FROM domingogallardo/playframework
WORKDIR /code
ADD . /code
RUN sbt clean stage
EXPOSE 9000
CMD target/universal/stage/bin/mads-todolist-2017 -Dplay.crypto.secret=$SECRET
