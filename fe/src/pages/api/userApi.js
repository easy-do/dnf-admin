import { fetchPost } from '@/utils/fetchUtil';

export default function loginRequest(url, data) {
  return fetchPost(url, data);
}