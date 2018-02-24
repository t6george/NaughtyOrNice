from flask import Flask, render_template

app = Flask('test')

@app.route("/start")

def webprint():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(host = '0.0.0.0', port = 3000)
