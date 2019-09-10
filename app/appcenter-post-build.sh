if [ "$AGENT_JOBSTATUS" == "Succeeded" ]; then
    set -e
    APPKNOX_ACCESS_TOKEN={appknox_token}
    curl -L https://github.com/appknox/appknox-go/releases/download/0.0.2/appknox-`uname -s`-x86_64 > appknox && chmod +x appknox
    ./appknox upload $(APPCENTER_OUTPUT_DIRECTORY)/app-debug.apk
    rm -f appknox
fi
