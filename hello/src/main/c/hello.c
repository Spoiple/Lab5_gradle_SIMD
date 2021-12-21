#include <jni.h>
#include <stdio.h>
#include <immintrin.h>
#include <time.h>

JNIEXPORT void JNICALL
Java_he1027_1lab5_HelloWorld_print5(JNIEnv *env, jobject obj, jintArray arr, jintArray dstArr, jint window, jint level)
{

//    clock_t start, end;
//    double cpu_time_used;
//    start = clock();
    jsize len = (*env)->GetArrayLength(env, arr);
    jint *array = (*env)->GetIntArrayElements(env, arr, 0);
    jint *dstArray = (*env)->GetIntArrayElements(env, dstArr, 0);

//    printf("\n%d", len);
    __m256i mLevel = _mm256_set1_epi8( level );
    __m256i mWindow = _mm256_set1_epi8( window );
    __m256i mSlope = _mm256_set1_epi16( (255 << 8) / ( window - level ) );

    __m256i levelMask;
    __m256i windowMask;
    __m256i wiLeXOr;
    __m256i result;
    __m256i pixel;

    __m256i tmp;
    __m256i tmp1;
    __m128i m128_v0;
    __m128i m128_v1;
    __m128i shift = _mm_set1_epi64((__m64) (uint64_t) 8);
    __m256i permMask = _mm256_set_epi32( 0b111, 0b110, 0b011, 0b010, 0b101, 0b100, 0b001, 0b000 );

    for (int i = 0; i < len/8; i++) {
        pixel = _mm256_load_si256( &array[i*8] );

        // Mask and set bits with value below level
        levelMask = _mm256_max_epu8( pixel, mLevel );
        levelMask = _mm256_cmpeq_epi8( pixel, levelMask );
        result = _mm256_and_si256( pixel, levelMask );

        // Mask and set bits with value above window
        windowMask = _mm256_max_epu8( pixel, mWindow );
        windowMask = _mm256_cmpeq_epi8( pixel, windowMask );
        result = _mm256_or_si256( result, windowMask );

        // Mask bits for multiplication
        wiLeXOr = _mm256_xor_si256( levelMask, windowMask );

        // Prepare contrast change by - level
        tmp = _mm256_and_si256( result, wiLeXOr );
        tmp = _mm256_subs_epu8( tmp, mLevel ); // - level

        // clear targeted pixels
        result = _mm256_and_si256( result, ~wiLeXOr );

        // Extract 4 bits for multiplication  // Can skip and only adjust 4 pixels each loop?
        m128_v0 = _mm256_extracti128_si256 ( tmp, 0 );
        m128_v1 = _mm256_extracti128_si256 ( tmp, 1 );

        // Unpack from 8bit to 16bit.
        // Change color values, shifted integer multiplication to avoid floats
        tmp = _mm256_cvtepu8_epi16( m128_v0 );
        tmp = _mm256_mullo_epi16( mSlope, tmp );
        tmp = _mm256_srl_epi16( tmp, shift );
        tmp1 = _mm256_cvtepu8_epi16( m128_v1 );
        tmp1 = _mm256_mullo_epi16( mSlope, tmp1 );
        tmp1 = _mm256_srl_epi16( tmp1, shift );

        // Repack from 16- to 8-bit, no loss in precision since all relevant values are sub 255
        tmp = _mm256_packus_epi16( tmp, tmp1 );
        // Reorder from repack
        tmp = _mm256_permutevar8x32_epi32(tmp, permMask);

        // Clear irrelevant bits and set new values of result
        tmp = _mm256_and_si256( tmp, wiLeXOr );
        result = _mm256_or_si256( result, tmp );

        _mm256_stream_si256( &dstArray[i*8], result );
    }
    (*env)->SetIntArrayRegion( env, dstArr, 0, len, dstArray );

//    end = clock();
//    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
//    printf("%f\n", cpu_time_used);
    return;
}


//JNIEXPORT void JNICALL
//Java_he1027_1lab5_HelloWorld_print5(JNIEnv *env, jobject obj, jobjectArray arr, jint windows, jint levels)
//{
//
////    clock_t start, end;
////    double cpu_time_used;
////    start = clock();
//    // TODO: ändra till endimensionell array
//    const jint iLevel = levels;
//    const jint iWindow = windows;
//    unsigned int iSlope = (255 << 8) / (iWindow - iLevel);
//    const uint64_t shift = 8;
//    jintArray intArray = (jintArray) (*env)->GetObjectArrayElement(env, arr, 0);
//    jsize len1 = (*env)->GetArrayLength(env, arr);
//    jsize len2 = (*env)->GetArrayLength(env, intArray);
//    jint *array;
//
//    __m256i level = _mm256_set1_epi8(iLevel); // set level
//    __m256i window = _mm256_set1_epi8(iWindow); // set window
//    __m256i slope = _mm256_set1_epi16(iSlope); // set slope
//
//    __m256i levelMask;
//    __m256i windowMask;
//    __m256i wiLeXOr;
//    __m256i result;
//    __m256i pixel;
//
//    __m256i tmp;
//    __m256i tmp1;
//    __m128i m128_v0;
//    __m128i m128_v1;
//    __m256i permMask = _mm256_set_epi32( 0b111, 0b110, 0b011, 0b010, 0b101, 0b100, 0b001, 0b000 );
//
////    jint *array = (*env)->GetIntArrayElements(env, arr, 0);
//    for (int j = 0; j < len1; j++) {
//        intArray = (jintArray) (*env)->GetObjectArrayElement(env, arr, j);
//        array = (*env)->GetIntArrayElements(env, intArray, 0);
//
//        for (int i = 0; i < len2/8; i++) {
//            pixel = _mm256_maskload_epi32(&array[i*8], _mm256_set1_epi32(0x80000000));
//            levelMask = _mm256_max_epu8(pixel, level); // compensate for unsigned
//            levelMask = _mm256_cmpeq_epi8(pixel, levelMask); // compare to result from above
//
//            result = _mm256_and_si256(pixel, levelMask); // set pixels
//
//            windowMask = _mm256_max_epu8(pixel, window); // compensate for unsigned
//            windowMask = _mm256_cmpeq_epi8(pixel, windowMask); // compare to result from above
//
//            result = _mm256_or_si256(result, windowMask); // set pixels
//
//            wiLeXOr = _mm256_xor_si256(levelMask, windowMask); // mask for contrast adjustment
//
//            // change contrast
//            tmp = _mm256_and_si256(result, wiLeXOr);
//            tmp = _mm256_subs_epu8( tmp, level ); // - level
//
//
//            // clear targeted pixels
//        //    wiLeXOr = _mm256_sub_epi8(_mm256_set1_epi8(0xFF), wiLeXOr); // invert mask
//            result = _mm256_and_si256(result, ~wiLeXOr); // clear targeted pixels
//
//            m128_v0 = _mm256_extracti128_si256 ( tmp, 0 );
//            m128_v1 = _mm256_extracti128_si256 ( tmp, 1 );
//
//
//            tmp = _mm256_cvtepu8_epi16( m128_v0 );
//            tmp = _mm256_mullo_epi16(slope, tmp);
//            tmp = _mm256_srl_epi16(tmp, _mm_set1_epi64((__m64) shift));
//    //        m128_v0 = _mm256_extracti128_si256 ( tmp, 1 );
//            tmp1 = _mm256_cvtepu8_epi16( m128_v1 );
//            tmp1 = _mm256_mullo_epi16(slope, tmp1);
//            tmp1 = _mm256_srl_epi16(tmp1, _mm_set1_epi64((__m64) shift));
//
//            tmp = _mm256_packus_epi16(tmp, tmp1);
//            // sortera
//            tmp = _mm256_permutevar8x32_epi32(tmp, permMask);
//            // TODO: vad är detta?
//        //    __m256i result2 = _mm256_unpackhi_epi8(tmp, tmp1);
//        //    __m256i result2 = tmp;
//
//        //    wiLeXOr = _mm256_sub_epi8(_mm256_set1_epi8(0xFF), wiLeXOr);
//            tmp = _mm256_and_si256(tmp, wiLeXOr);
//            result = _mm256_or_si256(result, tmp);
//
//            // TODO: sist i varje inre loop store i array[i]
//            _mm256_maskstore_epi32(&array[i*8], _mm256_set1_epi32(0x80000000), result);
//        }
//        // TODO: sist i varje yttre loop sätt yttre array
//        (*env)->SetIntArrayRegion(env, intArray, 0, len2, array);
//
////        (*env)->SetObjectArrayElement(env, arr, (jsize) j, intArray);
//    }
//
//
////    return arr;
//
//
////    end = clock();
////    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
////    printf("%f\n", cpu_time_used);
//    return;
//}



JNIEXPORT void JNICALL Java_he1027_1lab5_HelloWorld_print(JNIEnv *env, jobject obj)
{
    __m256i A = _mm256_set_epi32(2,3,4,5,6,7,8,9);
    __m256i B = _mm256_set_epi32(2,2,2,2,2,2,2,2);
    __m256i result = _mm256_add_epi32(A, B);

    int a[] = {2,3,4,5,6,7,8,9};
    int b[] = {2,2,2,2,2,2,2,2};
    int r[8];
    for (int i = 0; i < 8; i++) {
        r[i] = a[i] + b[i];
    }

    int *c = (int*)&result;
    for (int i = 0; i < 8; i++) {
//        std::cout << r[i] << std::endl;
//        std::cout << c[i] << std::endl;
        printf("%d\n", c[i]);
    }
    for (int i = 0; i < 8; i++) {
    //        std::cout << r[i] << std::endl;
    //        std::cout << c[i] << std::endl;
            printf("%d\n", r[i]);
        }

    printf("AVX2!\n");
    return;
}
JNIEXPORT void JNICALL Java_he1027_1lab5_HelloWorld_print2(JNIEnv *env, jobject obj)
{
    printf("KORV\n");
    return;
}

//JNIEXPORT jobjectArray JNICALL Java_he1027_1lab5_HelloWorld_print3(
//JNIEnv *env,
//jobject, int row, int col, int val)
//{
//    cells[row][col] = val;
//
//    // Get the int array class
//    jclass cls = env->FindClass("[I");
//
//    jintArray iniVal = env->NewIntArray(3);
//    // Create the returnable jobjectArray with an initial value
//    jobjectArray outer = env->NewObjectArray(3,cls, iniVal);
//
//    for (int i = 0; i < 3; i++)
//    {
//        jintArray inner = env->NewIntArray(3);
//        env->SetIntArrayRegion(inner, 0, 3, cells[i]);
//        // set inner's values
//        env->SetObjectArrayElement(outer, i, inner);
//        env->DeleteLocalRef(inner);
//    }
//    return outer;
//}

//JNIEXPORT jobjectArray JNICALL
//        Java_he1027_1lab5_HelloWorld_print3(
//                JNIEnv *env, jobject /* this */,
//                jobjectArray list) {
//
//         //Finding arrayList class and float class(2 lists , one x and another is y)
//            static jclass arrayListCls = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
//            jclass floatCls = env->FindClass("java/lang/Float");
//         //env initialization of list object and float
//            static jmethodID listConstructor = env->GetMethodID(arrayListCls, "<init>", "(I)V");
//            jmethodID alGetId  = env->GetMethodID(arrayListCls, "get", "(I)Ljava/lang/Object;");
//            jmethodID alSizeId = env->GetMethodID(arrayListCls, "size", "()I");
//            static jmethodID addElementToList = env->GetMethodID(arrayListCls, "add", "(Ljava/lang/Object;)Z");
//
//            jmethodID floatConstructor = env->GetMethodID( floatCls, "<init>", "(F)V");
//            jmethodID floatId = env->GetMethodID(floatCls,"floatValue", "()F");
//
//
//        //null check(if null then return)
//        if (arrayListCls == nullptr || floatCls == nullptr) {
//            return 0;
//        }
//
//    //     Get the value of each Float list object in the array
//        jsize length = env->GetArrayLength(list);
//
//        //If empty
//        if (length < 1) {
//            env->DeleteLocalRef(arrayListCls);
//            env->DeleteLocalRef(floatCls);
//            return 0;
//        }
//
//// Creating an output jObjectArray
//    jobjectArray outJNIArray = env->NewObjectArray(length, arrayListCls, 0);
//
//        //taking list of X and Y points object at the time of return
//        jobject  xPoint,yPoint,xReturnObject,yReturnObject;
//
//            //getting the xList,yList object from the array
//            jobject xObjFloatList = env->GetObjectArrayElement(list, 0);
//            jobject yObjFloatList = env->GetObjectArrayElement(list, 1);
//
//
//     // number of elements present in the array object
//        int xPointCounts = static_cast<int>(env->CallIntMethod(xObjFloatList, alSizeId));
//
//        static jfloat xReturn, yReturn;
//                jobject xReturnArrayList = env->NewObject(arrayListCls,listConstructor,0);
//        jobject yReturnArrayList = env->NewObject(arrayListCls,listConstructor,0);
//
//    for (int j = 0; j < xPointCounts; j++) {
//            //Getting the x points from the x object list in the array
//            xPoint = env->CallObjectMethod(xObjFloatList, alGetId, j);
//            //Getting the y points from the y object list in the array
//            yPoint = env->CallObjectMethod(yObjFloatList, alGetId, j);
//
////Returning jobjectArray(Here I am returning the same x and points I am receiving from java side, just to show how to make the returning `jobjectArray`)
//
//            //float x and y values
//            xReturn =static_cast<jfloat >(env->CallFloatMethod(xPoint, floatId,j));
//            yReturn =static_cast<jfloat >(env->CallFloatMethod(yPoint, floatId,j));
//
//
//            xReturnObject = env->NewObject(floatCls,floatConstructor,xReturn);
//             yReturnObject = env->NewObject(floatCls,floatConstructor,yReturn);
//
//            env->CallBooleanMethod(xReturnArrayList,addElementToList,xReturnObject);
//
//
//            env->CallBooleanMethod(yReturnArrayList,addElementToList,yReturnObject);
//            env->SetObjectArrayElement(outJNIArray,0,xReturnArrayList);
//            env->SetObjectArrayElement(outJNIArray,1,yReturnArrayList);
//        __android_log_print(ANDROID_LOG_ERROR, "List of X and Y are saved in the array","%d", 3);
//
//    }
//
//    return outJNIArray;