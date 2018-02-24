import tensorflow as tf
import numpy as np

inputs=tf.placeholder('float',[None,2],name='Input')
targets=tf.placeholder('float',name='Target')

weight1=tf.Variable(tf.random_normal(shape=[2,3],stddev=0.02),name="Weight1")
biases1=tf.Variable(tf.random_normal(shape=[3],stddev=0.02),name="Biases1")

hLayer=tf.matmul(inputs,weight1)
hLayer=hLayer+biases1

hLayer=tf.sigmoid(hLayer, name='hActivation')

weight2=tf.Variable(tf.random_normal(shape=[3,1],stddev=0.02),name="Weight2")
biases2=tf.Variable(tf.random_normal(shape=[1],stddev=0.02),name="Biases2")

weight2=tf.Variable(tf.random_normal(shape=[3,1],stddev=0.02),name="Weight2")
biases2=tf.Variable(tf.random_normal(shape=[1],stddev=0.02),name="Biases2")

cost=tf.squared_difference(targets, output)
cost=tf.reduce_mean(cost)
optimizer=tf.train.AdamOptimizer().minimize(cost)

with tf.Session() as sess:
    tf.global_variables_initializer().run()
    for i in range(epochs):
        error,_ =sess.run([cost,optimizer],feed_dict={inputs: inp,targets:out})
        print(i,error)

     while True:
            a = input("type 1st input :")
            b = input("type 2nd input :")
            inp=[[a,b]]
            inp=np.array(inp)
            prediction=sess.run([output],feed_dict={inputs: inp})
            print(prediction)
