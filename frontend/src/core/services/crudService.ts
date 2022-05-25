import axios from "axios";

export type PageInfo = {
  number: number,
  size: number
}

export type PageResult<T> = {
  entities: T[],
  totalEntities: number,
  totalPages: number
}

export class CrudService<T, CT=T, UT=T> {

  constructor(public ID: string, public baseUrl: string) {}

  public async fetchAll(): Promise<T[]> {
    return (await axios.get(`${this.baseUrl}`)).data;
  }
  
  public async fetch(id: string): Promise<T> {
    return (await axios.get(`${this.baseUrl}/${id}`)).data;
  }
  
  public async add(entity: CT): Promise<T> {
    return (await axios.post(`${this.baseUrl}`, entity)).data;
  }
  
  public async update(id: string, entity: UT): Promise<T> {
    return (await axios.put(`${this.baseUrl}/${id}`, entity)).data;
  }
  
  public async delete(id: string): Promise<T> {
    return (await axios.delete(`${this.baseUrl}/${id}`)).data;
  }
}