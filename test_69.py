import tensorflow as tf
import numpy as np
import random
layers = [3,4,2]
weights = [tf.random_normal(shape = [y,x],stddev=0.4) for x,y in zip(layers[1:],layers[:-1])]
biases = [tf.random_normal(shape = [y,1], stddev=0.4) for y in layers[1:]]

test = list(zip(weights,biases))

print(test)

a = [2,0,3]
a = map(lambda x : x * 1.0,a)
print(list(a))
