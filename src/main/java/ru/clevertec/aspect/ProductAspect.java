//package ru.clevertec.aspect;
//
//import lombok.extern.java.Log;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import ru.clevertec.caches.Cacheable;
//import ru.clevertec.entity.Product;
//import ru.clevertec.factory.AlgorithmConfigFactory;
//
//import java.util.Optional;
//
//
//@Log
//@Aspect
//public class ProductAspect {
//
//    private final Cacheable<Product> cache = AlgorithmConfigFactory.getAlgorithmType();
//
//    @Around("@annotation(ru.clevertec.annotation.Cached) && " +
//            "execution(public * ru.clevertec.service.impl.ProductServiceImpl.getProduct(..))")
//    public Object getProductFromCache(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("Called getProductFromCache() from aspect class");
//
//        Object[] args = pjp.getArgs();
//        Long id = (Long) args[0];
//        Product product = cache.get(id);
//        if (product != null) {
//            return Optional.of(product);
//        }
//        Optional<Product> o = (Optional<Product>) pjp.proceed();
//        o.ifPresent(value -> cache.put(id, value));
//
//        return o;
//    }
//
//    @Around("@annotation(ru.clevertec.annotation.Cached) && " +
//            "execution(public * ru.clevertec.service.impl.ProductServiceImpl.addProduct(..))")
//    public Object addProductInCache(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("Called addProductInCache() from aspect class");
//
//        Object[] args = pjp.getArgs();
//        Product product = (Product) args[0];
//        pjp.proceed();
//
//        cache.put(product.getId(), product);
//        return product.getId();
//    }
//
//    @Around("@annotation(ru.clevertec.annotation.Cached) && " +
//            "execution(public * ru.clevertec.service.impl.ProductServiceImpl.removeProduct(..))")
//    public Object removeProductFromCache(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("Called removeProductFromCache() from aspect class");
//
//        Object[] args = pjp.getArgs();
//        Long id = (Long) args[0];
//        boolean isRemove = (boolean) pjp.proceed();
//        if (isRemove){
//            cache.remove(id);
//        }
//        return isRemove;
//    }
//
//    @Around("@annotation(ru.clevertec.annotation.Cached) && " +
//            "execution(public * ru.clevertec.service.impl.ProductServiceImpl.updateProduct(..))")
//    public Object updateProductInCache(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("Called updateProductInCache() from aspect class");
//
//        Object[] args = pjp.getArgs();
//        Product product = (Product) args[0];
//        boolean isUpdate = (boolean) pjp.proceed();
//        if (isUpdate) {
//            cache.put(product.getId(), product);
//        }
//
//        return isUpdate;
//    }
//
//}
