import tensorflow as tf
import numpy as np
import random


def sigmoid(z):
    return 1.0/(1.0 + tf.exp(-z))
def sigmoid_prime(z):
    return sigmoid(z)*(1-sigmoid(z))

class Network(object):

    def __init__(self,layers):
        self.num_layers = len(layers)
        self.layers = layers
        self.weights = [tf.random_normal(shape = [y,x],stddev=0.4) for x,y in zip(layers[1:],layers[:-1])]
        self.biases = [tf.random_normal(shape = [y,1], stddev=0.4) for y in layers[:-1]]
    def feedforward(self,inp):
        for bias,weight in zip(self.biases,self.weights):
            inp = sigmoid(tf.add(tf.matmul(inp,weight),bias))
        return inp
    def stochastic_gradient_descent(self,training_data,batch_size,epochs,rate,test_data=None):
        n = len(training_data)
        for epoch in range(epochs):
            random.shuffle(training_data)
            batches = [training_data[i:i+batch_size] for i in range(0,n,batch_size)]
            for batch in batches:
                b_differential = [tf.zeros(shape=bias.get_shape(),dtype='float32',name='nabla_b') for bias in self.biases]
                w_differential = [tf.zeros(shape=weight.get_shape(),dtype='float32',name='nabla_w') for weight in self.weights]
                for inp,out in batch:
                    d_b_differential, d_w_differential = self.backpropagate(inp,out)
                    w_differential = [i+j for i,j in zip(b_differential, d_b_differential)]                
                    w_differential = [i+j for i,j in zip(w_differential, d_w_differential)]                
            if test_data:
                test_count = len(test_data)
            if test_data:
                print('Epoch #:',epoch,'accurate to:',self.evaluate(test_data),'/',test_count)
            else:
                print('Epoch #:',epoch,'done')
            
        
        
            
    


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
